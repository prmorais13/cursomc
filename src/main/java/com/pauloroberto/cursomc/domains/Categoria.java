package com.pauloroberto.cursomc.domains;

import java.io.Serializable;

import lombok.Data;

@Data
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;

	public Categoria(){}
	
	public Categoria(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
}
