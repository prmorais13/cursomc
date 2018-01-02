package com.pauloroberto.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pauloroberto.cursomc.domains.Pedido;
import com.pauloroberto.cursomc.repositories.PedidoRepository;
import com.pauloroberto.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido find(Integer id) {
		Pedido pedido = this.pedidoRepository.findOne(id);
		
		if(pedido == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id " + id
					+ ", Tipo: " + Pedido.class.getName());
		}
		
		return pedido;
	}

}
