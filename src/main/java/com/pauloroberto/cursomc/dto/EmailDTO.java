package com.pauloroberto.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class EmailDTO  implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Email é obrigatório!")
	@Email(message = "Email inválido!")
	private String email;
}
