package br.com.brunobarbosa.rest.loja.services;

import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brunobarbosa.rest.loja.domain.Cliente;
import br.com.brunobarbosa.rest.loja.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> retornoCliente = clienteRepository.findById(id);
		return retornoCliente.orElseThrow(() -> new ObjectNotFoundException(
				"Id: " + id + ", Tipo: " + Cliente.class.getName(), "Objeto Não Encontrado"));
	}
	
}
