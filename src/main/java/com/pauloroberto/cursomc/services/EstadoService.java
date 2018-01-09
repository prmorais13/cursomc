package com.pauloroberto.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pauloroberto.cursomc.domains.Estado;
import com.pauloroberto.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Estado> findAll() {
		return this.estadoRepository.findAllByOrderByNome();
	}

}
