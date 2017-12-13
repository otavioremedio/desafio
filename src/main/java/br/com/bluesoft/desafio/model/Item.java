package br.com.bluesoft.desafio.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Item {

	private long id;
	private long quantidade;
    private BigDecimal preco;
    private Pedido pedido;
    private String gtin;
    
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="produto_pedido_id")
    public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(long quantidade) {
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
	
	public String getGtin() {
		return gtin;
	}
	public void setGtin(String gtin) {
		this.gtin = gtin;
	}
	@Override
	public String toString() {
		return "{id=" + id + ", quantidade=" + quantidade + ", preco=" + preco + ", pedido=" + pedido + ", gtin="
				+ gtin + "}";
	}
	
	
}
