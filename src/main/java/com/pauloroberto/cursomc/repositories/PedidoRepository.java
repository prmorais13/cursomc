package com.pauloroberto.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pauloroberto.cursomc.domains.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

}
