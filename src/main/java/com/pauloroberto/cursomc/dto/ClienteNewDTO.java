package com.pauloroberto.cursomc.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ClienteNewDTO {
	
	@NotEmpty(message = "Nome é obrigatório!")
	@Length(min = 5, max = 120, message = "O nome deve ter entre 5 e 120 caracteres!")
	private String nome;
	
	@NotEmpty(message = "Email é obrigatório!")
	@Email(message = "Email inválido!")
	private String email;
	private String cpfOuCnpj;
	private Integer tipo;
	
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	
	private String telefone1;
	private String telefone2;
	private String telefone3;
	
	private Integer cidadeId;
	
}
