package com.pauloroberto.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.pauloroberto.cursomc.domains.Cliente;
import com.pauloroberto.cursomc.domains.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	void sendEmail(SimpleMailMessage msg);
	void sendNewPasswordEmail(Cliente cliente, String newPassword);

}
