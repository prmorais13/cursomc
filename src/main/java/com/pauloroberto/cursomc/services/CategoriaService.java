package com.pauloroberto.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pauloroberto.cursomc.domains.Categoria;
import com.pauloroberto.cursomc.repositories.CategoriaRepository;
import com.pauloroberto.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscar(Integer id) {
		Categoria categoria = this.categoriaRepository.findOne(id);
		
		if(categoria == null) {
			throw new ObjectNotFoundException("Objeto não encontrada! Id " + id
					+ ", Tipo: " + Categoria.class.getName());
		}
		
		return categoria;
	}

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return this.categoriaRepository.save(categoria);
	}

}
