package br.com.bluesoft.desafio.services;

import java.util.List;

import br.com.bluesoft.desafio.model.Item;
import br.com.bluesoft.desafio.model.Pedido;

public interface PedidoService {

	/**
	 * Retorna os pedidos criados.
	 *
	 * @param List<Item> itens
	 * @return List<Pedido>
	 */
	List<Pedido> criarPedidos(List<Item> itens);

	/**
	 * Retorna os pedidos criados.
	 *
	 * @param
	 * @return List<Pedido>
	 */
	List<Pedido> listarPedidos();

}
