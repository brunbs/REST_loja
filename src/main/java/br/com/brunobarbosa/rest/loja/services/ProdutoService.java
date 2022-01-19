package br.com.brunobarbosa.rest.loja.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.brunobarbosa.rest.loja.domain.Categoria;
import br.com.brunobarbosa.rest.loja.domain.Produto;
import br.com.brunobarbosa.rest.loja.repositories.CategoriaRepository;
import br.com.brunobarbosa.rest.loja.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto buscar(Integer id) {
		Optional<Produto> retornoProduto = produtoRepository.findById(id);
		return retornoProduto.orElseThrow(() -> new ObjectNotFoundException(
				"Id: " + id + ", Tipo: " + Produto.class.getName(), "Objeto Não Encontrado"));
	}
	
	public Page<Produto> busca(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return produtoRepository.busca(nome, categorias, pageRequest);
		
	}
	
}
