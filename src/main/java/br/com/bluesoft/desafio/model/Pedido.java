package br.com.bluesoft.desafio.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido {

	private long id;
	private List<ProdutoPedido> produtos;
	private Fornecedor fornecedor;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "pedido_id")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<ProdutoPedido> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<ProdutoPedido> produtos) {
		this.produtos = produtos;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

}
