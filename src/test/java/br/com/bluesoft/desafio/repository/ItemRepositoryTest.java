package br.com.bluesoft.desafio.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.bluesoft.desafio.model.Fornecedor;
import br.com.bluesoft.desafio.model.Item;
import br.com.bluesoft.desafio.model.Produto;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ItemRepositoryTest {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Before
	public void setUp() throws Exception {
		Fornecedor fornecedor = this.fornecedorRepository.save(obterDadosFornecedor());
		Produto produto = this.produtoRepository.findByGtin("7894900011517");
		this.itemRepository.save(obterDadosItem(produto, fornecedor));
	}

	@After
	public void tearDown() throws Exception {
		this.fornecedorRepository.deleteAll();
		this.produtoRepository.deleteAll();
		this.itemRepository.deleteAll();
	}

	@Test
	public void testBuscarItens() {
		List<Item> itens = this.itemRepository.findAll();
		assertEquals(1, itens.size());
	}
//
//	@Test
//	public void testBuscarLancamentosPorFuncionarioIdPaginado() {
//		PageRequest page = new PageRequest(0, 10);
//		Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, page);
//
//		assertEquals(2, lancamentos.getTotalElements());
//	}

	private Item obterDadosItem(Produto produto, Fornecedor fornecedor) {
		Item item = new Item();
	    item.setProduto(produto);
	    item.setFornecedor(fornecedor);
	    item.setQuantidade(4);
		item.setPreco(3.45);

		return item;
	}

	private Fornecedor obterDadosFornecedor() {
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setNome("Fornecedor 1");
		fornecedor.setCnpj("51463645000100");
		return fornecedor;
	}

}
