package br.com.brunobarbosa.rest.loja.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.brunobarbosa.rest.loja.domain.Cidade;
import br.com.brunobarbosa.rest.loja.domain.Cliente;
import br.com.brunobarbosa.rest.loja.domain.Endereco;
import br.com.brunobarbosa.rest.loja.domain.enums.TipoCliente;
import br.com.brunobarbosa.rest.loja.dto.ClienteDTO;
import br.com.brunobarbosa.rest.loja.dto.ClienteNovoDTO;
import br.com.brunobarbosa.rest.loja.repositories.ClienteRepository;
import br.com.brunobarbosa.rest.loja.repositories.EnderecoRepository;
import br.com.brunobarbosa.rest.loja.services.exceptions.DataIntegrityException;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> retornoCliente = clienteRepository.findById(id);
		return retornoCliente.orElseThrow(() -> new ObjectNotFoundException(
				"Id: " + id + ", Tipo: " + Cliente.class.getName(), "Objeto Não Encontrado"));
	}
	
	@Transactional
	public Cliente inserir(Cliente cliente) {
		cliente.setId(null);
		cliente = clienteRepository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
	}
	
	public Cliente atualizar(Cliente cliente) {
		Cliente novoCliente = buscar(cliente.getId());
		atualizaDados(novoCliente, cliente);
		return clienteRepository.save(novoCliente);
	}
	
	public void deletar(Integer id) {
		buscar(id);
		try {
			clienteRepository.deleteById(id);	
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Cliente que possui pedidos!");
		}
	}
	
	public List<Cliente> buscarTodos() {
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> buscaComPaginacao(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDto) {
		return new Cliente(clienteDto.getId(), clienteDto.getNome(), clienteDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNovoDTO clienteNovoDto) {
		Cliente cli = new Cliente(null, clienteNovoDto.getNome(), clienteNovoDto.getEmail(), clienteNovoDto.getCpfOuCnpj(), TipoCliente.toEnum(clienteNovoDto.getTipo()), pe.encode(clienteNovoDto.getSenha()));
		Cidade cid = new Cidade(clienteNovoDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, clienteNovoDto.getLogradouro(), clienteNovoDto.getNumero(), clienteNovoDto.getComplemento(), clienteNovoDto.getBairro(), clienteNovoDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(clienteNovoDto.getTelefone1());
		if(clienteNovoDto.getTelefone2() != null) {
			cli.getTelefones().add(clienteNovoDto.getTelefone2());
		}
		if(clienteNovoDto.getTelefone3() != null) {
			cli.getTelefones().add(clienteNovoDto.getTelefone3());
		}
		return cli;
	}
	
	private void atualizaDados(Cliente novoCliente, Cliente cliente) {
		novoCliente.setNome(cliente.getNome());
		novoCliente.setEmail(cliente.getEmail());
	}
	
	
	
}
