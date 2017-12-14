package br.com.bluesoft.desafio.model;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.CascadeType;


@Entity
public class Item {

	private long id;
	private long quantidade;
    private BigDecimal preco;
    private Produto produto;
    private BigDecimal total;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="item_id")
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

	public BigDecimal getTotal() {
		return new BigDecimal(this.quantidade * this.preco.doubleValue(), MathContext.DECIMAL64);
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Override
	public String toString() {
		return "{id=" + id + ", quantidade=" + quantidade + ", preco=" + preco + ", produto=" + produto + ", total=" + total + "}";
	}

}
