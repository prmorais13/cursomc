package com.pauloroberto.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pauloroberto.cursomc.domains.Estado;
import com.pauloroberto.cursomc.dto.EstadoDTO;
import com.pauloroberto.cursomc.services.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService estadoService;
	
	@GetMapping
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> list = this.estadoService.findAll();
		List<EstadoDTO> listDto = list.stream().map(estado -> new EstadoDTO(estado)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);
	}

}
