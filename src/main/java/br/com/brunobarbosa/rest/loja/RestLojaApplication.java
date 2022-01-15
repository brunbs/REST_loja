package br.com.brunobarbosa.rest.loja;

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
import br.com.brunobarbosa.rest.loja.domain.Produto;
import br.com.brunobarbosa.rest.loja.domain.enums.TipoCliente;
import br.com.brunobarbosa.rest.loja.repositories.CategoriaRepository;
import br.com.brunobarbosa.rest.loja.repositories.CidadeRepository;
import br.com.brunobarbosa.rest.loja.repositories.ClienteRepository;
import br.com.brunobarbosa.rest.loja.repositories.EnderecoRepository;
import br.com.brunobarbosa.rest.loja.repositories.EstadoRepository;
import br.com.brunobarbosa.rest.loja.repositories.ProdutoRepository;

@SpringBootApplication
public class RestLojaApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository catRepository;
	
	@Autowired
	private ProdutoRepository prodRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(RestLojaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto prod1 = new Produto(null, "Computador", 2000.00);
		Produto prod2 = new Produto(null, "Impressora", 800.00);
		Produto prod3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2));
		
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategorias().addAll(Arrays.asList(cat1));
		
		catRepository.saveAll(Arrays.asList(cat1, cat2));
		prodRepository.saveAll(Arrays.asList(prod1, prod2, prod3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria", "maria@email.com", "45612370", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("51511111", "566787750"));
		Endereco e1 = new Endereco(null, "Rua A", "300", "Apto 101", "Bairro A", "5563247", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua B", "67", "Apto 301", "Bairro B", "5321354", cli1, c2);
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
	}

}
