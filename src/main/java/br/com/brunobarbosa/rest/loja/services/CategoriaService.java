package br.com.brunobarbosa.rest.loja.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.brunobarbosa.rest.loja.domain.Categoria;
import br.com.brunobarbosa.rest.loja.dto.CategoriaDTO;
import br.com.brunobarbosa.rest.loja.repositories.CategoriaRepository;
import br.com.brunobarbosa.rest.loja.services.exceptions.DataIntegrityException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> retornoCategoria = categoriaRepository.findById(id);
		return retornoCategoria.orElseThrow(() -> new ObjectNotFoundException(
				"Id: " + id + ", Tipo: " + Categoria.class.getName(), "Objeto Não Encontrado"));
	}
	
	public Categoria inserir(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepository.save(categoria);
	}
	
	public Categoria atualizar(Categoria categoria) {
		Categoria novaCategoria = buscar(categoria.getId());
		atualizaDados(novaCategoria, categoria);
		return categoriaRepository.save(novaCategoria);
	}
	
	public void deletar(Integer id) {
		buscar(id);
		try {
			categoriaRepository.deleteById(id);	
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos!");
		}
	}
	
	public List<Categoria> buscarTodos() {
		return categoriaRepository.findAll();
	}
	
	public Page<Categoria> buscaComPaginacao(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return categoriaRepository.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}
	
	private void atualizaDados(Categoria novaCategoria, Categoria categoria) {
		novaCategoria.setNome(categoria.getNome());
	}
	
}
