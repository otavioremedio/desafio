package br.com.bluesoft.desafio.repository;


import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
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
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.Produto;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PedidoRepositoryTest {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Before
	public void setUp() throws Exception {
		this.pedidoRepository.save(obterDadosPedido());
	}

	@After
	public void tearDown() throws Exception {
		this.pedidoRepository.deleteAll();
		this.fornecedorRepository.deleteAll();
	}

	@Test
	public void testListarPedido() {
		List<Pedido> pedidos = this.pedidoRepository.findAll();
		assertNotNull(pedidos);
	}

	private Pedido obterDadosPedido() {
		Pedido pedido = new Pedido();
		Item item = new Item();
		List<Item> itensPedido = new ArrayList<Item>();
		item.setProduto(this.produtoRepository.findByGtin("7894900011517"));
		item.setQuantidade(4);
		item.setPreco(new BigDecimal(3.45, MathContext.DECIMAL64).setScale(2, RoundingMode.CEILING));
		itensPedido.add(item);
		pedido.setItens(itensPedido);
		pedido.setFornecedor(obterDadosFornecedor());

		return pedido;
	}

	private Fornecedor obterDadosFornecedor() {
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setNome("Fornecedor 1");
		fornecedor.setCnpj("51463645000100");
		return fornecedor;
	}

}
