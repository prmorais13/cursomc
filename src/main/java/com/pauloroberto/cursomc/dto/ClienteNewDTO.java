package com.pauloroberto.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.pauloroberto.cursomc.services.validation.ClienteInsert;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@ClienteInsert //Anotação customizada
public class ClienteNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Nome é obrigatório!")
	@Length(min = 5, max = 120, message = "O nome deve ter entre 5 e 120 caracteres!")
	private String nome;
	
	@NotEmpty(message = "Email é obrigatório!")
	@Email(message = "Email inválido!")
	private String email;
	
	@NotEmpty(message = "CPF ou CNPJ é obrigatório!")
	private String cpfOuCnpj;
	private Integer tipo;
	
	@NotEmpty(message = "Logradouro é obrigatório!")
	private String logradouro;
	
	@NotEmpty(message = "Número é obrigatório!")
	private String numero;
	private String complemento;
	
	// @NotEmpty(message = "Nome é obrigatório!")
	private String bairro;
	
	@NotEmpty(message = "Telefone é obrigatório!")
	private String cep;
	
	@NotEmpty(message = "Nome é obrigatório!")
	private String telefone1;
	private String telefone2;
	private String telefone3;
	
	private Integer cidadeId;
	
}
