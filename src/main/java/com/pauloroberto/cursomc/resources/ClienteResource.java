package com.pauloroberto.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pauloroberto.cursomc.domains.Cliente;
import com.pauloroberto.cursomc.dto.ClienteDTO;
import com.pauloroberto.cursomc.dto.ClienteNewDTO;
import com.pauloroberto.cursomc.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	// Busca todos os clientes
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list =  this.clienteService.findAll();
		
		List<ClienteDTO> listDto = list.stream()
				.map(cliente -> new ClienteDTO(cliente))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);
	}

	// Busca cliente por id
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente cliente =  this.clienteService.find(id);
		return ResponseEntity.ok().body(cliente);
	}
	
	// Busca clientes com paginação
	@GetMapping("/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "24") int size,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
		
		Page<Cliente> list =  this.clienteService.findPage(page, size, direction, orderBy);
		
		Page<ClienteDTO> listDto = list.map(cliente -> new ClienteDTO(cliente));
		
		return ResponseEntity.ok().body(listDto);
	}
	
	// Insere novo cliente
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO clienteNewDto) {
		Cliente cliente = this.clienteService.fromNewDto(clienteNewDto);
		cliente = this.clienteService.insert(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				  .path("/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	// Atualiza cliente
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO clienteDto,@PathVariable Integer id) {
		Cliente cliente = this.clienteService.fromDto(clienteDto);
		cliente.setId(id);
		cliente = this.clienteService.update(cliente);
		return ResponseEntity.noContent().build();
	}
	
	// Exclui cliente
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		this.clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
