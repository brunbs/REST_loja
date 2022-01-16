package br.com.brunobarbosa.rest.loja.services;

import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brunobarbosa.rest.loja.domain.Pedido;
import br.com.brunobarbosa.rest.loja.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> retornoPedido = pedidoRepository.findById(id);
		return retornoPedido.orElseThrow(() -> new ObjectNotFoundException(
				"Id: " + id + ", Tipo: " + Pedido.class.getName(), "Objeto Não Encontrado"));
	}
	
}
