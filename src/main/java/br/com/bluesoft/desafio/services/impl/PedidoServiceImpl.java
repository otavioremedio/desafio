package br.com.bluesoft.desafio.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.bluesoft.desafio.dtos.CotacaoDto;
import br.com.bluesoft.desafio.dtos.PrecoDto;
import br.com.bluesoft.desafio.model.Fornecedor;
import br.com.bluesoft.desafio.model.Item;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.repository.FornecedorRepository;
import br.com.bluesoft.desafio.repository.ItemRepository;
import br.com.bluesoft.desafio.repository.PedidoRepository;
import br.com.bluesoft.desafio.repository.ProdutoRepository;
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

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Override
	public List<Pedido> criarPedidos(List<Item> itens) throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		List<CotacaoDto> cotacoes = null;
		List<CotacaoDto> melhoresCotacoes = new ArrayList<CotacaoDto>();
		List<Pedido> pedidosCriados = new ArrayList<Pedido>();

		itens = itens.stream().filter(x -> x.getQuantidade() > 0).collect(Collectors.toList());

		for (Item item : itens) {
			//consulta cotacao do produto
			Produto produto = this.produtoRepository.findByGtin(item.getProduto().getGtin());

			ResponseEntity<List<CotacaoDto>> cotacoesResponse = restTemplate.exchange(props.getString("cotacao_path") + produto.getGtin()
                    , HttpMethod.GET, null, new ParameterizedTypeReference<List<CotacaoDto>>(){});

			cotacoes = cotacoesResponse.getBody();

			//pega o menor preço de cada fornecedor conforme quantidade minima e quantidade solicitada
			for (CotacaoDto cotacao : cotacoes) {
				Optional<PrecoDto> menorPreco = cotacao.getPrecos().stream().filter(x -> (item.getQuantidade() - x.getQuantidade_minima()) >= 0).findFirst();
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
				cotacoes.get(0).setGtin(item.getProduto().getGtin());
				cotacoes.get(0).setQuantidade(item.getQuantidade());
				melhoresCotacoes.add(cotacoes.get(0));
			} else {
				throw new Exception("Nenhum fornecedor encontrado para a quantidade solicitada do produto " + produto.getNome());
			}
		}

		//agrupa por cnpj
		Map<String, List<CotacaoDto>> cotacoesPorCnpj = melhoresCotacoes.stream().collect(Collectors.groupingBy(CotacaoDto::getCnpj));

		//prepara os pedidos
		cotacoesPorCnpj.forEach((k, v) -> {
			ArrayList<CotacaoDto> cotacoesPedido = (ArrayList<CotacaoDto>) v;
			List<Item> itensPedido = new ArrayList<Item>();
			Pedido pedido = new Pedido();
			String cnpj = "";
			String nmFornecedor = "";
			Fornecedor fornecedor;

			for (CotacaoDto cotacaoDto : cotacoesPedido) {
				Item item = new Item();
				item.setProduto(this.produtoRepository.findByGtin(cotacaoDto.getGtin()));
				item.setPreco(cotacaoDto.getPrecos().get(0).getPreco());
				item.setQuantidade(cotacaoDto.getQuantidade());
				itensPedido.add(item);
				cnpj = cotacaoDto.getCnpj();
				nmFornecedor = cotacaoDto.getNome();
			}

			fornecedor = this.fornecedorRepository.findByCnpj(cnpj);

			if (fornecedor == null) {
				fornecedor = new Fornecedor();
				fornecedor.setCnpj(cnpj);
				fornecedor.setNome(nmFornecedor);
			}

			pedido.setItens(itensPedido);
			pedido.setFornecedor(fornecedor);
			pedidosCriados.add(this.pedidoRepository.save(pedido));
		});

		return pedidosCriados;
	}

	@Override
	public List<Pedido> listarPedidos() throws Exception {
		List<Pedido> listaPedidos = new ArrayList<Pedido>();
		listaPedidos = this.pedidoRepository.findAll();

		if(listaPedidos.size() == 0)
			throw new Exception("Não existem pedidos criados");

		return listaPedidos;
	}



}
