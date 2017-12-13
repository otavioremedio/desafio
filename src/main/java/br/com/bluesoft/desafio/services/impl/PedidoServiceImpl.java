package br.com.bluesoft.desafio.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.hibernate.mapping.Array;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.datatype.jdk8.OptionalDoubleSerializer;

import br.com.bluesoft.desafio.dtos.CotacaoDto;
import br.com.bluesoft.desafio.dtos.PrecoDto;
import br.com.bluesoft.desafio.model.Fornecedor;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.ProdutoPedido;
import br.com.bluesoft.desafio.repository.FornecedorRepository;
import br.com.bluesoft.desafio.repository.PedidoRepository;
import br.com.bluesoft.desafio.services.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {

	private static final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);
	private static final ResourceBundle props = ResourceBundle.getBundle("application");


	//adicionar os repositorios necessarios
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Override
	public List<Pedido> criarPedidos(List<ProdutoPedido> produtosPedido) {
		
		RestTemplate restTemplate = new RestTemplate();
		List<CotacaoDto> cotacoes = null;
		List<CotacaoDto> melhoresCotacoes = new ArrayList<CotacaoDto>();
		List<Pedido> pedidosCriados = new ArrayList<Pedido>();
		
		try {		
			produtosPedido = produtosPedido.stream().filter(x -> x.getQuantidade() > 0).collect(Collectors.toList());

			for (ProdutoPedido produtoPedido : produtosPedido) {
				//consulta cotacao do produto
				ResponseEntity<List<CotacaoDto>> cotacoesResponse = restTemplate.exchange(props.getString("cotacao_path") + produtoPedido.getGtin()
	                    , HttpMethod.GET, null, new ParameterizedTypeReference<List<CotacaoDto>>(){});

				cotacoes = cotacoesResponse.getBody();

				//pega o menor preço de cada fornecedor conforme quantidade minima e quantidade solicitada
				for (CotacaoDto cotacao : cotacoes) {
					Optional<PrecoDto> menorPreco = cotacao.getPrecos().stream().filter(x -> (produtoPedido.getQuantidade() - x.getQuantidade_minima()) >= 0).findFirst();
					if(menorPreco.isPresent()){
						cotacao.getPrecos().clear();
						cotacao.getPrecos().add(menorPreco.get());
					} else {
						cotacoes.remove(cotacao);
						
						if (cotacoes.size() == 0) {
							break;
						}
					}												
				}

				//ordena do menor pro maior
				//posicao 0 pois nesse momento só existe o valor mais baixo de cada fornecedor
				if(cotacoes.size()> 0){
					Comparator<CotacaoDto> cotacaoComparator = (c1,c2) -> c1.getPrecos().get(0).getPreco().compareTo(c2.getPrecos().get(0).getPreco());
					cotacoes.sort(cotacaoComparator);

					//posicao 0 para pegar a menor cotacao de todos os fornecedores
					cotacoes.get(0).setGtin(produtoPedido.getGtin());
					cotacoes.get(0).setQuantidade(produtoPedido.getQuantidade());
					melhoresCotacoes.add(cotacoes.get(0));
				}				
			}

			//agrupa por cnpj
			Map<String, List<CotacaoDto>> cotacoesPorCnpj = melhoresCotacoes.stream().collect(Collectors.groupingBy(CotacaoDto::getCnpj));

			//prepara os pedidos
			cotacoesPorCnpj.forEach((k, v) -> {
				ArrayList<CotacaoDto> lCotacoes = (ArrayList<CotacaoDto>) v;
				List<ProdutoPedido> produtos = new ArrayList<ProdutoPedido>();
				Pedido pedido = new Pedido();				
				String cnpj = "";
				String nmFornecedor = "";
				Fornecedor fornecedor;
				
				for (CotacaoDto cotacaoDto : lCotacoes) {
					ProdutoPedido produtoPedido = new ProdutoPedido();
					produtoPedido.setGtin(cotacaoDto.getGtin());
					produtoPedido.setPreco(cotacaoDto.getPrecos().get(0).getPreco());
					produtoPedido.setQuantidade(cotacaoDto.getQuantidade());
					produtos.add(produtoPedido);
					cnpj = cotacaoDto.getCnpj();
					nmFornecedor = cotacaoDto.getNome();
				}
				
				pedido.setProdutos(produtos);
				fornecedor = this.fornecedorRepository.findByCnpj(cnpj);
				
				if (fornecedor == null) {
					fornecedor = new Fornecedor();
					fornecedor.setCnpj(cnpj);
					fornecedor.setNome(nmFornecedor);
					fornecedor = this.fornecedorRepository.save(fornecedor);
				}
				
				pedido.setFornecedor(fornecedor);
				pedidosCriados.add(this.pedidoRepository.save(pedido));					
			});		
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return pedidosCriados;		
	}

}
