package br.com.bluesoft.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import br.com.bluesoft.desafio.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
	@Transactional(readOnly = true)
	Fornecedor findByCnpj(String cnpj);
}
