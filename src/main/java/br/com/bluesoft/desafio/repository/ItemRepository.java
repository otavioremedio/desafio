package br.com.bluesoft.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bluesoft.desafio.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
