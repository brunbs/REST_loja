package br.com.brunobarbosa.rest.loja.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.brunobarbosa.rest.loja.domain.Cliente;
import br.com.brunobarbosa.rest.loja.dto.ClienteDTO;
import br.com.brunobarbosa.rest.loja.dto.ClienteNovoDTO;
import br.com.brunobarbosa.rest.loja.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente clienteBuscado = clienteService.buscar(id);
		return ResponseEntity.ok(clienteBuscado);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO ClienteDTO) {
		Cliente Cliente = clienteService.fromDTO(ClienteDTO);
		Cliente.setId(id);
		Cliente = clienteService.atualizar(Cliente);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		clienteService.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> buscaTodos() {
		List<Cliente> listaDeClientes = clienteService.buscarTodos();
		List<ClienteDTO> listaDTO = listaDeClientes.stream().map(Cliente -> new ClienteDTO(Cliente)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> buscaComPaginacao(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC")String direction) {
		Page<Cliente> listaDeClientes = clienteService.buscaComPaginacao(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listaDTO = listaDeClientes.map(Cliente -> new ClienteDTO(Cliente));
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNovoDTO clienteNovoDto) {
		Cliente cliente = clienteService.fromDTO(clienteNovoDto);
		cliente = clienteService.inserir(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
