package br.com.bluesoft.desafio.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.ProdutoPedido;
import br.com.bluesoft.desafio.services.PedidoService;


@RestController
@RequestMapping("/api/novo-pedido")
public class PedidoController {

	private static final Logger log = LoggerFactory.getLogger(PedidoController.class);

	@Autowired
	private PedidoService pedidoService;

	@PostMapping
	public @ResponseBody List<Pedido> criarPedidos(@RequestBody List<ProdutoPedido> produtos){
		List<Pedido> pedidos = this.pedidoService.criarPedidos(produtos);
		return pedidos;
	}

}
