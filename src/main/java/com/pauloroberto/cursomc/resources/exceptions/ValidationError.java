package com.pauloroberto.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	@Getter
	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}
	
	public void addError(String fieldName, String messagem) {
		this.errors.add(new FieldMessage(fieldName, messagem));
	}

}
