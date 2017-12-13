package br.com.bluesoft.desafio.services.impl;

import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.bluesoft.desafio.dtos.CotacaoDto;
import br.com.bluesoft.desafio.dtos.PrecoDto;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.services.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {

	private static final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);
	private static final ResourceBundle props = ResourceBundle.getBundle("application");


	//adicionar os repositorios necessarios@Autowired

	@Override
	public List<Pedido> criarPedidos(List<Produto> produtos) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<CotacaoDto>> cotacoesResponse = restTemplate.exchange(props.getString("cotacao_path") + "7894900011517"
				                                            , HttpMethod.GET, null, new ParameterizedTypeReference<List<CotacaoDto>>(){});

		List<CotacaoDto> cotacoes = cotacoesResponse.getBody();
        for (CotacaoDto cotacao : cotacoes) {
			PrecoDto menorPreco = cotacao.getPrecos().stream().filter(x -> x.getQuantidade_minima() == 1).findFirst().get();
			cotacao.getPrecos().clear();
			cotacao.getPrecos().add(menorPreco);
		}
		return null;
	}

}
