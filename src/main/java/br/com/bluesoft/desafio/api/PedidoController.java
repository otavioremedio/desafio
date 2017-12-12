package br.com.bluesoft.desafio.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.bluesoft.desafio.model.Cotacao;
import br.com.bluesoft.desafio.model.Produto;


@RestController
@RequestMapping("/api/novo-pedido")
public class PedidoController {
	
	private static final Logger log = LoggerFactory.getLogger(PedidoController.class);
	
	@PostMapping
	public void consome(@RequestBody Iterable<Produto> produtos){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Cotacao[]> cotacao = restTemplate.getForEntity("https://egf1amcv33.execute-api.us-east-1.amazonaws.com/dev/produto/7894900011517", Cotacao[].class);
		log.info(cotacao.getBody().toString());
	}
	
	
	
}
