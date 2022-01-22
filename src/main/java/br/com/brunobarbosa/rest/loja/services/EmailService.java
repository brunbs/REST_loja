package br.com.brunobarbosa.rest.loja.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.brunobarbosa.rest.loja.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
	
}
