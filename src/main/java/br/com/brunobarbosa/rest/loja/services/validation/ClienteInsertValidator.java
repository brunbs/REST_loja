package br.com.brunobarbosa.rest.loja.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.brunobarbosa.rest.loja.domain.enums.TipoCliente;
import br.com.brunobarbosa.rest.loja.dto.ClienteNovoDTO;
import br.com.brunobarbosa.rest.loja.resources.exceptions.FieldMessage;
import br.com.brunobarbosa.rest.loja.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNovoDTO> {
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNovoDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !(BR.isValidCPF(objDto.getCpfOuCnpj()))) {
			list.add(new FieldMessage("CpfOuCnpj", "CPF inválido"));
		}
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !(BR.isValidCNPJ(objDto.getCpfOuCnpj()))) {
			list.add(new FieldMessage("CpfOuCnpj", "CNPJ inválido"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMsg()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}