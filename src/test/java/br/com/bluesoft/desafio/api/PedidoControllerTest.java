package br.com.bluesoft.desafio.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import br.com.bluesoft.desafio.model.Item;
import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.repository.FornecedorRepository;
import br.com.bluesoft.desafio.repository.PedidoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PedidoControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private FornecedorRepository fornecedorRepository;

	private static final String CRIAR_PEDIDOS_URL = "/api/novo-pedido";

	@After
	public void tearDown() throws Exception {
		this.pedidoRepository.deleteAll();
		this.fornecedorRepository.deleteAll();
	}

	String content;

	@Before
	public void setUp() throws Exception{
		List<Item> itens = new ArrayList<Item>();
		Item item = new Item();
		Produto produto = new Produto();
		Gson gson = new Gson();
		produto.setGtin("7894900011517");
		item.setProduto(produto);
		item.setQuantidade(3);
		itens.add(item);
	    content = gson.toJson(itens);
	}


	@Test
	public void testCriarPedidos() throws Exception {
		this.mvc.perform(post(CRIAR_PEDIDOS_URL)
	    		.accept(MediaType.APPLICATION_JSON)
	    		.contentType(MediaType.APPLICATION_JSON)
			    .content(content))
				.andDo(print())
			    .andExpect(status().isOk())
			    .andExpect(jsonPath("$[0].id").value(1));

	}
}
