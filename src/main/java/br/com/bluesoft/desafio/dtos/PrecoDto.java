package br.com.bluesoft.desafio.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PrecoDto {
	private float preco;
	private int quantidade_minima;
	
	public float getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
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