package com.pauloroberto.cursomc.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.pauloroberto.cursomc.domains.Cliente;
import com.pauloroberto.cursomc.services.validation.ClienteUpdate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@ClienteUpdate //Anotação customizada
public class ClienteDTO {

	private Integer id;
	
	@NotEmpty(message = "Nome é obrigatório!")
	@Length(min = 5, max = 120, message = "O nome deve ter entre 5 e 120 caracteres!")
	private String nome;
	
	@NotEmpty(message = "Email é obrigatório!")
	@Email(message = "Email inválido!")
	private String email;
	
	
	public ClienteDTO(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
	}
	
}
