package com.pauloroberto.cursomc.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class StandardError {

	@Setter @Getter
	private Integer status;
	@Setter @Getter
	private String msg;
	@Setter @Getter
	private Long timeStamp;
	
}
