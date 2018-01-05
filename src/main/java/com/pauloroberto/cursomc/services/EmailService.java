package com.pauloroberto.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.pauloroberto.cursomc.domains.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	void sendEmail(SimpleMailMessage msg);

}
