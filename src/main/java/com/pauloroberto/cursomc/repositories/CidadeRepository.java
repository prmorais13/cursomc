package com.pauloroberto.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pauloroberto.cursomc.domains.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Cidade WHERE obj.estado.id = :estadoId ORDER BY obj.nome")
	public List<Cidade> findCidades(@Param("estado_Id") Integer estado_id);
}
