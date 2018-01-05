package com.pauloroberto.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.pauloroberto.cursomc.domains.Pedido;

public abstract class AbstractEmailService implements EmailService{

	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		
		SimpleMailMessage sm = prepareSimpleEmailMessageFromPedido(pedido);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleEmailMessageFromPedido(Pedido pedido) {
		
		SimpleMailMessage sm = new SimpleMailMessage();
		
		sm.setTo(pedido.getCliente().getEmail());
		sm.setFrom(this.sender);
		sm.setSubject("Pedido confirmado. Código: " + pedido.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(pedido.toString());
		
		return sm;
	}
	
	
}
