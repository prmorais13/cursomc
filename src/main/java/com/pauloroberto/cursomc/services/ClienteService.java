package com.pauloroberto.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.pauloroberto.cursomc.domains.Cliente;
import com.pauloroberto.cursomc.dto.ClienteDTO;
import com.pauloroberto.cursomc.repositories.ClienteRepository;
import com.pauloroberto.cursomc.services.exceptions.DataIntegrityException;
import com.pauloroberto.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<Cliente> findAll() {
		return this.clienteRepository.findAll();
	}
	
	public Cliente find(Integer id) {
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
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}
	
	// Método auxiliar que cria uma Cliente usando uma ClienteDTO
	public Cliente fromDto(ClienteDTO clienteDto) {
		return new Cliente(clienteDto.getId(), clienteDto.getNome(), clienteDto.getEmail(), null, null);
	}

	private void updateData(Cliente clienteUpdate, Cliente cliente) {
		clienteUpdate.setNome(cliente.getNome());
		clienteUpdate.setEmail(cliente.getEmail());
		
	}
}
