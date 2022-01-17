package br.com.brunobarbosa.rest.loja.services;

import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brunobarbosa.rest.loja.domain.Categoria;
import br.com.brunobarbosa.rest.loja.repositories.CategoriaRepository;

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
	
}
