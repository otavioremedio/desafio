package br.com.bluesoft.desafio.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.bluesoft.desafio.model.Item;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.repository.FornecedorRepository;
import br.com.bluesoft.desafio.repository.PedidoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PedidoServiceTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private PedidoService pedidoService;

	@MockBean
	private PedidoRepository pedidoRepository;

	@MockBean
	private FornecedorRepository fornecedorRepository;

	List<Item> itens = new ArrayList<Item>();

	@Before
	public void setUp() throws Exception{
		Item item1 = new Item();
		Item item2 = new Item();
		Produto p1 = new Produto();
		Produto p2 = new Produto();
		p1.setGtin("7891991010856");
		p2.setGtin("7891000100103");
		item1.setProduto(p1);
		item1.setQuantidade(1);
		item2.setProduto(p2);
		item2.setQuantidade(3);

		itens.add(item1);
		itens.add(item2);
	}

	@After
	public void tearDown() throws Exception {
		this.pedidoRepository.deleteAll();
		this.fornecedorRepository.deleteAll();
	}

	@Test
	public void testCriarPedidos() throws Exception {
		List<Pedido> pedidosCriados = new ArrayList<Pedido>();
		BDDMockito.given(this.pedidoService.criarPedidos(itens)).willReturn(pedidosCriados);
	}

}
