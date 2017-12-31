package com.pauloroberto.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pauloroberto.cursomc.domains.Categoria;
import com.pauloroberto.cursomc.services.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;

	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Categoria categoria =  this.categoriaService.buscar(id);
		return ResponseEntity.ok().body(categoria);
	}
}
