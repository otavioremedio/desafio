package br.com.bluesoft.desafio.api;

import static org.assertj.core.api.Assertions.filter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.bluesoft.desafio.model.Cotacao;
import br.com.bluesoft.desafio.model.Preco;
import br.com.bluesoft.desafio.model.Produto;


@RestController
@RequestMapping("/api/novo-pedido")
public class PedidoController {
	
	private static final Logger log = LoggerFactory.getLogger(PedidoController.class);
	
	@PostMapping
	public @ResponseBody List<Cotacao> consome(@RequestBody Iterable<Produto> produtos){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Cotacao>> cotacoesResponse = restTemplate.exchange("https://egf1amcv33.execute-api.us-east-1.amazonaws.com/dev/produto/7894900011517" 
				                                                              , HttpMethod.GET, null, new ParameterizedTypeReference<List<Cotacao>>(){});
        List<Cotacao> cotacoes = cotacoesResponse.getBody();
        for (Cotacao cotacao : cotacoes) {
			Preco menorPreco = cotacao.getPrecos().stream().filter(x -> x.getQuantidade_minima() == 1).findFirst().get();
			cotacao.getPrecos().clear();
			cotacao.getPrecos().add(menorPreco);
		}
        
        //Cotacao c = cotacoes.stream().filter(x -> (x.getPrecos().stream().filter(y -> y.getQuantidade_minima() == 1).findFirst().get()).getQuantidade_minima() == 1).findFirst().get();
        		
        return null;	
        //Comparator<Cotacao> comparaPreco = (Cotacao c1, Cotacao c2) -> (float)(c2.getPrecos() - c1.getPrecos());
       // cotacoes.sort(c1, c2) -> (float) ();
		 
	}
	
}
