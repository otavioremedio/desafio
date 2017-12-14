package br.com.bluesoft.desafio.repository;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.bluesoft.desafio.model.Fornecedor;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FornecedorRepositoryTest {

	@Autowired
	private FornecedorRepository fornecedorRepository;
	private static final String CNPJ = "51463645000100";

	@Before
	public void setUp() throws Exception {
		Fornecedor fornecedor = this.fornecedorRepository.save(obterDadosFornecedor());
	}

	@After
	public void tearDown() throws Exception {
		this.fornecedorRepository.deleteAll();
	}

	@Test
	public void testListarFornecedorPorCnpj() {
		Fornecedor fornecedor = this.fornecedorRepository.findByCnpj(CNPJ);
		assertEquals(CNPJ, fornecedor.getCnpj());
	}

	private Fornecedor obterDadosFornecedor() {
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setNome("Fornecedor 1");
		fornecedor.setCnpj(CNPJ);
		return fornecedor;
	}

}
