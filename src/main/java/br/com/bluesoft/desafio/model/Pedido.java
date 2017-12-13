package br.com.bluesoft.desafio.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido {

	private long id;
	private List<Item> itens;

	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "pedido_id")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Item> getItens() {
		return itens;
	}
	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

}
