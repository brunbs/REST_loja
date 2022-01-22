package br.com.brunobarbosa.rest.loja.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.brunobarbosa.rest.loja.services.DBService;
import br.com.brunobarbosa.rest.loja.services.EmailService;
import br.com.brunobarbosa.rest.loja.services.MockEmailService;
import br.com.brunobarbosa.rest.loja.services.SmtpEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instanciateDatabase() throws ParseException {
		dbService.instanciateTestDatabase();
		return true;
	}
	
	//@Bean
	//public EmailService emailService() {
	//	return new MockEmailService();
	//}
	
	@Bean
	public EmailService emailService () {
		return new SmtpEmailService();
	}
}
