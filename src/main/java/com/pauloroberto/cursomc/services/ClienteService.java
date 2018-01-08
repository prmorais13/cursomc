package com.pauloroberto.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pauloroberto.cursomc.domains.Cidade;
import com.pauloroberto.cursomc.domains.Cliente;
import com.pauloroberto.cursomc.domains.Endereco;
import com.pauloroberto.cursomc.domains.enums.Perfil;
import com.pauloroberto.cursomc.domains.enums.TipoCliente;
import com.pauloroberto.cursomc.dto.ClienteDTO;
import com.pauloroberto.cursomc.dto.ClienteNewDTO;
import com.pauloroberto.cursomc.repositories.CidadeRepository;
import com.pauloroberto.cursomc.repositories.ClienteRepository;
import com.pauloroberto.cursomc.repositories.EnderecoRepository;
import com.pauloroberto.cursomc.security.UserSS;
import com.pauloroberto.cursomc.services.exceptions.AuthorizationException;
import com.pauloroberto.cursomc.services.exceptions.DataIntegrityException;
import com.pauloroberto.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public List<Cliente> findAll() {
		return this.clienteRepository.findAll();
	}
	
	public Cliente find(Integer id) {
		
		UserSS usuarioLogado = UserService.userAuthenticated();
		if (usuarioLogado == null || !usuarioLogado.hasRole(Perfil.ADMIN) && !id.equals(usuarioLogado.getId())) {
			throw new AuthorizationException("Acesso Negado! O Usuário não é Administrador.");
		}
		
		Cliente cliente = this.clienteRepository.findOne(id);
		
		if(cliente == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id " + id
					+ ", Tipo: " + Cliente.class.getName());
		}
		
		return cliente;
	}
	
	public Page<Cliente> findPage(int page, int size, String direction, String orderBy) {
		PageRequest pageRequest = new PageRequest(page, size, Direction.valueOf(direction), orderBy);
		return this.clienteRepository.findAll(pageRequest);
	}
	
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente  = this.clienteRepository.save(cliente);
		this.enderecoRepository.save(cliente.getEnderecos());
		return cliente;
	}
	
	public Cliente update(Cliente cliente) {
		Cliente clienteUpdate = this.find(cliente.getId());
		updateData(clienteUpdate, cliente);
		
		return this.clienteRepository.save(clienteUpdate);
	}


	public void delete(Integer id) {
		this.find(id);
		
		try {
			this.clienteRepository.delete(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
		}
	}
	
	// Método auxiliar que cria um Cliente usando um ClienteDTO
	public Cliente fromDto(ClienteDTO clienteDto) {
		return new Cliente(clienteDto.getId(), clienteDto.getNome(), clienteDto.getEmail(), null, null, null);
	}
	
	public Cliente fromNewDto(ClienteNewDTO clienteNewDto) {
		Cliente cli = new Cliente(null, clienteNewDto.getNome(), clienteNewDto.getEmail(), clienteNewDto.getCpfOuCnpj(),
								  TipoCliente.toEnum(clienteNewDto.getTipo()), pe.encode(clienteNewDto.getSenha()));
		
		Cidade cid = this.cidadeRepository.findOne(clienteNewDto.getCidadeId());
		
		Endereco end = new Endereco(null, clienteNewDto.getLogradouro(), clienteNewDto.getNumero(), clienteNewDto.getComplemento(),
									clienteNewDto.getBairro(), clienteNewDto.getCep(), cli, cid);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().add(clienteNewDto.getTelefone1());
		
		if (clienteNewDto.getTelefone2() !=  null) {
			cli.getTelefones().add(clienteNewDto.getTelefone2());
		}
		
		if (clienteNewDto.getTelefone3() !=  null) {
			cli.getTelefones().add(clienteNewDto.getTelefone3());
		}
		return cli;
	}

	private void updateData(Cliente clienteUpdate, Cliente cliente) {
		clienteUpdate.setNome(cliente.getNome());
		clienteUpdate.setEmail(cliente.getEmail());
		
	}
}
