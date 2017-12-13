package br.com.bluesoft.desafio.dtos;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PrecoDto {
	private BigDecimal preco;
	private int quantidade_minima;

	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public int getQuantidade_minima() {
		return quantidade_minima;
	}
	public void setQuantidade_minima(int quantidade_minima) {
		this.quantidade_minima = quantidade_minima;
	}
	@Override
	public String toString() {
		return "{preco=" + preco + ", quantidade_minima=" + quantidade_minima + "}";
	}
}
