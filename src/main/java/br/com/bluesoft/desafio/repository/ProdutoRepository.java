package br.com.bluesoft.desafio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.bluesoft.desafio.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, String> {

	@Transactional(readOnly = true)
	Produto findByGtin(String gtin);
}
