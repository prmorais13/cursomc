package com.pauloroberto.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pauloroberto.cursomc.domains.Categoria;
import com.pauloroberto.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscar(Integer id) {
		return this.categoriaRepository.findOne(id);
	}

}
