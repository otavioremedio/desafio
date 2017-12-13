package br.com.bluesoft.desafio.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "fornecedor")
public class Fornecedor implements Serializable {

	private static final long serialVersionUID = 3960436649365666213L;

	private Long id;
	private String nome;
	private String cnpj;
	private List<Item> pedidos;

	public Fornecedor() {
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="fornecedor_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nome", nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "cnpj", nullable = false)
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Item> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Item> pedidos) {
		this.pedidos = pedidos;
	}

	@Override
	public String toString() {
		return "{id=" + id + ", nome=" + nome + ", cnpj=" + cnpj + "}";
	}

}
