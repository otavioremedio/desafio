package br.com.bluesoft.desafio.repository;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
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
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.model.ProdutoPedido;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProdutoPedidoRepositoryTest {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Before
	public void setUp() throws Exception {
		Fornecedor fornecedor = this.fornecedorRepository.save(obterDadosFornecedor());
		Produto produto = this.produtoRepository.findByGtin("7894900011517");
		this.pedidoRepository.save(obterDadosPedido(produto, fornecedor));
	}

	@After
	public void tearDown() throws Exception {
		this.fornecedorRepository.deleteAll();
		this.produtoRepository.deleteAll();
		this.pedidoRepository.deleteAll();
	}

	@Test
	public void testListarPedido() {
		List<Pedido> pedidos = this.pedidoRepository.findAll();
		assertEquals(1, pedidos.size());
	}
//
//	@Test
//	public void testBuscarLancamentosPorFuncionarioIdPaginado() {
//		PageRequest page = new PageRequest(0, 10);
//		Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, page);
//
//		assertEquals(2, lancamentos.getTotalElements());
//	}

	private Pedido obterDadosPedido(Produto produto, Fornecedor fornecedor) {
		Pedido pedido = new Pedido();
		ProdutoPedido produtoPedido = new ProdutoPedido();
		produtoPedido.setGtin(produto.getGtin());
		produtoPedido.setQuantidade(4);
		produtoPedido.setPreco(new BigDecimal(3.45));
		pedido.getProdutos().add(produtoPedido);
		pedido.setFornecedor(fornecedor);

		return pedido;
	}

	private Fornecedor obterDadosFornecedor() {
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setNome("Fornecedor 1");
		fornecedor.setCnpj("51463645000100");
		return fornecedor;
	}

}
