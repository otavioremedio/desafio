package br.com.bluesoft.desafio.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CotacaoDto {
	private String cnpj;
	private List<PrecoDto> precos;
	private String nome;
	private String gtin;
	private long quantidade;

	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public List<PrecoDto> getPrecos() {
		return precos;
	}
	public void setPrecos(List<PrecoDto> precos) {
		this.precos = precos;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getGtin() {
		return gtin;
	}
	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public long getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(long quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public String toString() {
		return "{cnpj=" + cnpj + ", precos=" + precos + ", nome=" + nome + ", gtin=" + gtin
				+ ", quantidade=" + quantidade + "}";
	}

}
