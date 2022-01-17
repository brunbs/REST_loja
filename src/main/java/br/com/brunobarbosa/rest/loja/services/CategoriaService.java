package br.com.brunobarbosa.rest.loja.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.brunobarbosa.rest.loja.domain.Categoria;
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
		buscar(categoria.getId());
		return categoriaRepository.save(categoria);
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
	
}
