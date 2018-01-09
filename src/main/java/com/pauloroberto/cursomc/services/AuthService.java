package com.pauloroberto.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pauloroberto.cursomc.domains.Cliente;
import com.pauloroberto.cursomc.repositories.ClienteRepository;
import com.pauloroberto.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	
	public void sendNewPassword(String email) {
		
		Cliente cliente = this.clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado!");
		}
		
		String newPassword = newPassword();
		cliente.setSenha(this.bCryptPasswordEncoder.encode(newPassword));
		
		this.clienteRepository.save(cliente);
		this.emailService.sendNewPasswordEmail(cliente, newPassword);
		
	}


	private String newPassword() {
		char[] vet = new char[10];
		
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		
		return new String(vet);
	}


	private char randomChar() {
		int opt = rand.nextInt(3);
		
		if (opt == 0) {// gera um digito
			return (char) (rand.nextInt(10) + 48);
		}
		else if (opt == 1){ //gera letra maiúscula
			return (char) (rand.nextInt(26) + 65);
		}
		else { // gera letra minúscula
			return (char) (rand.nextInt(26) + 97);
		}
	}

}
