package com.pauloroberto.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pauloroberto.cursomc.domains.Categoria;
import com.pauloroberto.cursomc.domains.Cidade;
import com.pauloroberto.cursomc.domains.Cliente;
import com.pauloroberto.cursomc.domains.Endereco;
import com.pauloroberto.cursomc.domains.Estado;
import com.pauloroberto.cursomc.domains.ItemPedido;
import com.pauloroberto.cursomc.domains.Pagamento;
import com.pauloroberto.cursomc.domains.PagamentoComBoleto;
import com.pauloroberto.cursomc.domains.PagamentoComCartao;
import com.pauloroberto.cursomc.domains.Pedido;
import com.pauloroberto.cursomc.domains.Produto;
import com.pauloroberto.cursomc.domains.enums.EstadoPagamento;
import com.pauloroberto.cursomc.domains.enums.TipoCliente;
import com.pauloroberto.cursomc.repositories.CategoriaRepository;
import com.pauloroberto.cursomc.repositories.CidadeRepository;
import com.pauloroberto.cursomc.repositories.ClienteRepository;
import com.pauloroberto.cursomc.repositories.EnderecoRepository;
import com.pauloroberto.cursomc.repositories.EstadoRepository;
import com.pauloroberto.cursomc.repositories.ItemPedidoRepository;
import com.pauloroberto.cursomc.repositories.PagamentoRepository;
import com.pauloroberto.cursomc.repositories.PedidoRepository;
import com.pauloroberto.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepositoy;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		this.categoriaRepository.save(Arrays.asList(cat1, cat2));
		this.produtoRepository.save(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Paraíba");
		Estado est2 = new Estado(null, "Rio Grande do Norte");
		
		Cidade c1 = new Cidade(null, "João Pessoa", est1);
		Cidade c2 = new Cidade(null, "Natal", est2);
		Cidade c3 = new Cidade(null, "Parnamirim", est2);
		
		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		this.estadoRepository.save(Arrays.asList(est1, est2));
		this.cidadeRepository.save(Arrays.asList(c1, c2, c3));
		
		
		Cliente cli1 = new Cliente(null, "Paulo Roberto", "prmorais_13@hotmail.com", "39135810491", TipoCliente.PESSOAFISICA);
		
		Endereco e1 = new Endereco(null, "Rua Parque Paraúna", "79", "Parque Verde", "Nova Esperança", "59144170", cli1, c3);
		Endereco e2 = new Endereco(null, "Rua Princesa Isabel", "799", null, "Cidade Alta", "59025400", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli1.getTelefones().addAll(Arrays.asList("32328877", "987015547"));
		
		this.clienteRepository.save(cli1);
		this.enderecoRepository.save(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("13/02/2018 13:00"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("04/05/2018 22:50"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped2, 1);
		ped2.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped1, sdf.parse("04/06/2018 22:50"), null);
		ped1.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		this.pedidoRepository.save(Arrays.asList(ped1, ped2));
		this.pagamentoRepositoy.save(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 1, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		this.itemPedidoRepository.save(Arrays.asList(ip1, ip2, ip3));
	}
}