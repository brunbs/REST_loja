package br.com.brunobarbosa.rest.loja;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.brunobarbosa.rest.loja.domain.Categoria;
import br.com.brunobarbosa.rest.loja.domain.Cidade;
import br.com.brunobarbosa.rest.loja.domain.Cliente;
import br.com.brunobarbosa.rest.loja.domain.Endereco;
import br.com.brunobarbosa.rest.loja.domain.Estado;
import br.com.brunobarbosa.rest.loja.domain.ItemPedido;
import br.com.brunobarbosa.rest.loja.domain.Pagamento;
import br.com.brunobarbosa.rest.loja.domain.PagamentoComBoleto;
import br.com.brunobarbosa.rest.loja.domain.PagamentoComCartao;
import br.com.brunobarbosa.rest.loja.domain.Pedido;
import br.com.brunobarbosa.rest.loja.domain.Produto;
import br.com.brunobarbosa.rest.loja.domain.enums.EstadoPagamento;
import br.com.brunobarbosa.rest.loja.domain.enums.TipoCliente;
import br.com.brunobarbosa.rest.loja.repositories.CategoriaRepository;
import br.com.brunobarbosa.rest.loja.repositories.CidadeRepository;
import br.com.brunobarbosa.rest.loja.repositories.ClienteRepository;
import br.com.brunobarbosa.rest.loja.repositories.EnderecoRepository;
import br.com.brunobarbosa.rest.loja.repositories.EstadoRepository;
import br.com.brunobarbosa.rest.loja.repositories.ItemPedidoRepository;
import br.com.brunobarbosa.rest.loja.repositories.PagamentoRepository;
import br.com.brunobarbosa.rest.loja.repositories.PedidoRepository;
import br.com.brunobarbosa.rest.loja.repositories.ProdutoRepository;

@SpringBootApplication
public class RestLojaApplication implements CommandLineRunner{
	
	public static void main(String[] args) {
		SpringApplication.run(RestLojaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
	}

}
