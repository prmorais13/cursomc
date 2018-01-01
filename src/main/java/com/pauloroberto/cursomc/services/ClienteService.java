package com.pauloroberto.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pauloroberto.cursomc.domains.Cliente;
import com.pauloroberto.cursomc.repositories.ClienteRepository;
import com.pauloroberto.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente buscar(Integer id) {
		Cliente cliente = this.clienteRepository.findOne(id);
		
		if(cliente == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id " + id
					+ ", Tipo: " + Cliente.class.getName());
		}
		
		return cliente;
	}

}
