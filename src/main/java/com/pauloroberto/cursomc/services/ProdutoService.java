package com.pauloroberto.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.pauloroberto.cursomc.domains.Categoria;
import com.pauloroberto.cursomc.domains.Produto;
import com.pauloroberto.cursomc.repositories.CategoriaRepository;
import com.pauloroberto.cursomc.repositories.ProdutoRepository;
import com.pauloroberto.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto find(Integer id) {
		Produto produto = this.produtoRepository.findOne(id);
		
		if(produto == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id " + id
					+ ", Tipo: " + Produto.class.getName());
		}
		
		return produto;
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, int page, int size, String direction, String orderBy) {
		
		PageRequest pageRequest = new PageRequest(page, size, Direction.valueOf(direction), orderBy);
		
		List<Categoria> categorias = this.categoriaRepository.findAll(ids);
		
		return this.produtoRepository.search(nome, categorias, pageRequest);
		
	}

}
