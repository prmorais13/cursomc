package com.pauloroberto.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.pauloroberto.cursomc.domains.Categoria;
import com.pauloroberto.cursomc.repositories.CategoriaRepository;
import com.pauloroberto.cursomc.services.exceptions.DataIntegrityException;
import com.pauloroberto.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<Categoria> findAll() {
		return this.categoriaRepository.findAll();
	}
	
	public Categoria find(Integer id) {
		Categoria categoria = this.categoriaRepository.findOne(id);
		
		if(categoria == null) {
			throw new ObjectNotFoundException("Objeto não encontrada! Id " + id
					+ ", Tipo: " + Categoria.class.getName());
		}
		
		return categoria;
	}
	
	public Page<Categoria> findPage(int page, int size, String direction, String orderBy) {
		PageRequest pageRequest = new PageRequest(page, size, Direction.valueOf(direction), orderBy);
		return this.categoriaRepository.findAll(pageRequest);
	}

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return this.categoriaRepository.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		this.find(categoria.getId());
		return this.categoriaRepository.save(categoria);
	}

	public void delete(Integer id) {
		this.find(id);
		
		try {
			this.categoriaRepository.delete(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}


}
