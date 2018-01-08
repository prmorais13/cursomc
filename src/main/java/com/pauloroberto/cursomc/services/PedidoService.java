package com.pauloroberto.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.pauloroberto.cursomc.domains.Cliente;
import com.pauloroberto.cursomc.domains.ItemPedido;
import com.pauloroberto.cursomc.domains.PagamentoComBoleto;
import com.pauloroberto.cursomc.domains.Pedido;
import com.pauloroberto.cursomc.domains.enums.EstadoPagamento;
import com.pauloroberto.cursomc.repositories.ClienteRepository;
import com.pauloroberto.cursomc.repositories.ItemPedidoRepository;
import com.pauloroberto.cursomc.repositories.PagamentoRepository;
import com.pauloroberto.cursomc.repositories.PedidoRepository;
import com.pauloroberto.cursomc.repositories.ProdutoRepository;
import com.pauloroberto.cursomc.security.UserSS;
import com.pauloroberto.cursomc.services.exceptions.AuthorizationException;
import com.pauloroberto.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EmailService emailService;

	public Pedido find(Integer id) {
		Pedido pedido = this.pedidoRepository.findOne(id);

		if (pedido == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id " + id + ", Tipo: " + Pedido.class.getName());
		}

		return pedido;
	}

	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		
		// Busca o Cliente no banco pelo ID
		pedido.setCliente(this.clienteRepository.getOne(pedido.getCliente().getId()));
		
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
		}
		
		this.pedidoRepository.save(pedido);
		this.pagamentoRepository.save(pedido.getPagamento());
		
		for (ItemPedido ip : pedido.getItens()) {
			ip.setDesconto(0.0);
			
			// Busca o Produto no banco pelo ID
			ip.setProduto(this.produtoRepository.findOne(ip.getProduto().getId()));
			
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(pedido);
		}
		
		this.itemPedidoRepository.save(pedido.getItens());
		
		this.emailService.sendOrderConfirmationEmail(pedido);
		
		return pedido;
	}
	
	public Page<Pedido> findPage(int page, int size, String direction, String orderBy) {
		UserSS usuarioLogado = UserService.userAuthenticated();
		if (usuarioLogado == null) {
			throw new AuthorizationException("Acesso negado! Usuário não está logado.");
		}
		
		PageRequest pageRequest = new PageRequest(page, size, Direction.valueOf(direction), orderBy);
		
		Cliente cliente = this.clienteRepository.findOne(usuarioLogado.getId());
		
		return this.pedidoRepository.findByCliente(cliente, pageRequest);
	}

}
