package br.com.bluesoft.desafio.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PedidoControllerTest {

	@Autowired
	private MockMvc mvc;

	private static final String CRIAR_PEDIDOS_URL = "/api/novo-pedido";

	@Test
	public void testCriarPedidos() throws Exception {

		List<Item> itens = new ArrayList<Item>();
		Item item = new Item();
		Produto produto = new Produto();
		produto.setGtin("7894900011517");
		item.setProduto(produto);
		item.setQuantidade(3);
		itens.add(item);

		Gson gson = new Gson();
	    String content = gson.toJson(itens);

	    this.mvc.perform(post(CRIAR_PEDIDOS_URL)
	    		.accept(MediaType.APPLICATION_JSON)
	    		.contentType(MediaType.APPLICATION_JSON)
			    .content(content))
				.andDo(print())
			    .andExpect(status().isOk())
			    .andExpect(jsonPath("$[0].id").value(1));

	}
}
