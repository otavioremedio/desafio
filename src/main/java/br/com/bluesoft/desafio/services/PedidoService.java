package br.com.bluesoft.desafio.services;

import java.util.List;

import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.Produto;

public interface PedidoService {

	/**
	 * Retorna os pedidos criados.
	 *
	 * @param produtos
	 * @return Optional<List<Pedido>>
	 */
	List<Pedido> criarPedidos(List<Produto> produtos);

}
