package br.com.bluesoft.desafio.services.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import br.com.bluesoft.desafio.model.ProdutoPedido;
import br.com.bluesoft.desafio.services.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {

	private static final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);
	private static final ResourceBundle props = ResourceBundle.getBundle("application");


	//adicionar os repositorios necessarios@Autowired

	@Override
	public List<Pedido> criarPedidos(List<ProdutoPedido> produtosPedido) {
		RestTemplate restTemplate = new RestTemplate();
		List<CotacaoDto> cotacoes = null;
		List<CotacaoDto> melhoresCotacoes = null;

		produtosPedido = produtosPedido.stream().filter(x -> x.getQuantidade() > 0).collect(Collectors.toList());

		for (ProdutoPedido produtoPedido : produtosPedido) {
			//consulta cotacao do produto
			ResponseEntity<List<CotacaoDto>> cotacoesResponse = restTemplate.exchange(props.getString("cotacao_path") + produtoPedido.getGtin()
                    , HttpMethod.GET, null, new ParameterizedTypeReference<List<CotacaoDto>>(){});

			cotacoes = cotacoesResponse.getBody();

			//pega o menor preço de cada fornecedor conforme quantidade minima e quantidade solicitada
			for (CotacaoDto cotacao : cotacoes) {
				PrecoDto menorPreco = cotacao.getPrecos().stream().filter(x -> (produtoPedido.getQuantidade() - x.getQuantidade_minima()) >= 0).findFirst().get();
				cotacao.getPrecos().clear();
				cotacao.getPrecos().add(menorPreco);
			}

			//ordena do menor pro maior
			//posicao 0 pois nesse momento só existe o valor mais baixo de cada fornecedor
			Comparator<CotacaoDto> cotacaoComparator = (c1,c2) -> c1.getPrecos().get(0).getPreco().compareTo(c2.getPrecos().get(0).getPreco());
			cotacoes.sort(cotacaoComparator.reversed());

			//posicao 0 para pegar a menor cotacao de todos os fornecedores
			cotacoes.get(0).setGtin(produtoPedido.getGtin());
			cotacoes.get(0).setQuantidade(produtoPedido.getQuantidade());
			melhoresCotacoes.add(cotacoes.get(0));
		}

		//agrupa por cnpj
		Map<String, List<CotacaoDto>> cotacoesPorCnpj = melhoresCotacoes.stream().collect(Collectors.groupingBy(CotacaoDto::getCnpj));

		//prepara os pedidos
		cotacoesPorCnpj.forEach((k, v) -> System.out.print("item: " + k + " Count: " + v));

		Stream.of(cotacoesPorCnpj.entrySet().toArray()).forEach(System.out::println);

		return null;
	}

}
