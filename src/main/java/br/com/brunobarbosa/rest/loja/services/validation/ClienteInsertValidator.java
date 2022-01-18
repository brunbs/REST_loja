package br.com.brunobarbosa.rest.loja.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.brunobarbosa.rest.loja.domain.Cliente;
import br.com.brunobarbosa.rest.loja.domain.enums.TipoCliente;
import br.com.brunobarbosa.rest.loja.dto.ClienteNovoDTO;
import br.com.brunobarbosa.rest.loja.repositories.ClienteRepository;
import br.com.brunobarbosa.rest.loja.resources.exceptions.FieldMessage;
import br.com.brunobarbosa.rest.loja.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNovoDTO> {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNovoDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !(BR.isValidCPF(objDto.getCpfOuCnpj()))) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !(BR.isValidCNPJ(objDto.getCpfOuCnpj()))) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMsg()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}