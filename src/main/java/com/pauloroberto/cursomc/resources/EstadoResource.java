package com.pauloroberto.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pauloroberto.cursomc.domains.Cidade;
import com.pauloroberto.cursomc.domains.Estado;
import com.pauloroberto.cursomc.dto.CidadeDTO;
import com.pauloroberto.cursomc.dto.EstadoDTO;
import com.pauloroberto.cursomc.services.CidadeService;
import com.pauloroberto.cursomc.services.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> list = this.estadoService.findAll();
		List<EstadoDTO> listDto = list.stream().map(estado -> new EstadoDTO(estado)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping("/{estadoId}/cidades")
	public ResponseEntity<List<CidadeDTO>> findAll(@PathVariable Integer estadoId) {
		List<Cidade> list = this.cidadeService.findByEstado(estadoId);
		List<CidadeDTO> listDto = list.stream().map(cidade -> new CidadeDTO(cidade)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);
	}

}
