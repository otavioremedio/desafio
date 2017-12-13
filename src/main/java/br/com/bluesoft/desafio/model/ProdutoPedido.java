package br.com.bluesoft.desafio.model;

import java.math.BigDecimal;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

public class ProdutoPedido extends Produto {

	private int quantidade;
    private BigDecimal preco;
    private Pedido pedido;

    public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}


}
