package br.com.brunobarbosa.rest.loja.services;

import java.util.Date;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.brunobarbosa.rest.loja.domain.ItemPedido;
import br.com.brunobarbosa.rest.loja.domain.PagamentoComBoleto;
import br.com.brunobarbosa.rest.loja.domain.Pedido;
import br.com.brunobarbosa.rest.loja.domain.enums.EstadoPagamento;
import br.com.brunobarbosa.rest.loja.repositories.ItemPedidoRepository;
import br.com.brunobarbosa.rest.loja.repositories.PagamentoRepository;
import br.com.brunobarbosa.rest.loja.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> retornoPedido = pedidoRepository.findById(id);
		return retornoPedido.orElseThrow(() -> new ObjectNotFoundException(
				"Id: " + id + ", Tipo: " + Pedido.class.getName(), "Objeto Não Encontrado"));
	}
	
	@Transactional
	public Pedido inserir(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(clienteService.buscar(pedido.getCliente().getId()));
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
		}
		pedido = pedidoRepository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		for(ItemPedido ip : pedido.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.buscar(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(pedido);
		}
		itemPedidoRepository.saveAll(pedido.getItens());
		emailService.sendOrderConfirmationEmail(pedido);
		return pedido;
	}
	
}
