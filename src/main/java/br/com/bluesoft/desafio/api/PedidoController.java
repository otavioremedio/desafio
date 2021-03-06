package br.com.bluesoft.desafio.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.bluesoft.desafio.model.Item;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.services.PedidoService;


@RestController

public class PedidoController {

	private static final Logger log = LoggerFactory.getLogger(PedidoController.class);

	@Autowired
	private PedidoService pedidoService;

	@PostMapping
	@ResponseBody
	@RequestMapping("/api/novo-pedido")
	public List<Pedido> criarPedidos(@RequestBody List<Item> itens) throws Exception {
		return this.pedidoService.criarPedidos(itens);
	}

	@GetMapping
	@ResponseBody
	@RequestMapping("/api/pedidos")
	public List<Pedido> listarPedido() throws Exception{
		return this.pedidoService.listarPedidos();
	}
}
