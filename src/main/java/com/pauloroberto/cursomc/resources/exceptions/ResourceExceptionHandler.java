package com.pauloroberto.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pauloroberto.cursomc.services.exceptions.AuthorizationException;
import com.pauloroberto.cursomc.services.exceptions.DataIntegrityException;
import com.pauloroberto.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest req) {
		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
												"Não encontrado", e.getMessage(), req.getRequestURI());		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest req) {
		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
												"Integridade de dados", e.getMessage(), req.getRequestURI());		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest req) {
		
		ValidationError error = new ValidationError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(),
													"Erro de validação", e.getMessage(), req.getRequestURI());
		
		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			error.addError(fieldError.getField(), fieldError.getDefaultMessage());
		}		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorizationException(AuthorizationException e, HttpServletRequest req) {
		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(),
												"Acesso negado", e.getMessage(), req.getRequestURI());	
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}

}
