package com.pauloroberto.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.pauloroberto.cursomc.domains.Cidade;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CidadeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message = "Nome é obrigatório!")
	@Length(min = 5, max = 80, message = "O nome deve ter entre 5 e 80 caracteres!")
	private String nome;
	
	public CidadeDTO(Cidade cidade) {
		this.id = cidade.getId();
		this.nome = cidade.getNome();
	}
	
}
