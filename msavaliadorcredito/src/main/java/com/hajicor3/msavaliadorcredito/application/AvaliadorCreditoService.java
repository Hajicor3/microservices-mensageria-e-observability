package com.hajicor3.msavaliadorcredito.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hajicor3.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import com.hajicor3.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import com.hajicor3.msavaliadorcredito.application.ex.ErroSolicitacaoCartaoExcepetion;
import com.hajicor3.msavaliadorcredito.domain.model.Cartao;
import com.hajicor3.msavaliadorcredito.domain.model.CartaoAprovado;
import com.hajicor3.msavaliadorcredito.domain.model.CartoesCliente;
import com.hajicor3.msavaliadorcredito.domain.model.DadosCliente;
import com.hajicor3.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;
import com.hajicor3.msavaliadorcredito.domain.model.ProtocoloSolicitacaoCartao;
import com.hajicor3.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import com.hajicor3.msavaliadorcredito.domain.model.SituacaoCliente;
import com.hajicor3.msavaliadorcredito.infra.clients.CartoesResourceClient;
import com.hajicor3.msavaliadorcredito.infra.clients.ClienteResourceClient;
import com.hajicor3.msavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;

import feign.FeignException.FeignClientException;

@Service
public class AvaliadorCreditoService {
	
	@Autowired
	private ClienteResourceClient clienteResource;
	
	@Autowired
	private CartoesResourceClient cartoesResource;
	
	@Autowired
	private SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;
	
	public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
		try {
		ResponseEntity<DadosCliente> dadosClienteResponse = clienteResource.clientByCpf(cpf);
		ResponseEntity<List<CartoesCliente>> cartoesResponse = cartoesResource.listaCartoesByCliente(cpf);
		
		return SituacaoCliente
		.builder()
		.cliente(dadosClienteResponse.getBody())
		.cartoes(cartoesResponse.getBody())
		.build();
		}catch(FeignClientException e) {
			int status = e.status();
			if(HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			
			throw new ErroComunicacaoMicroservicesException(e.getMessage(),status);
		}
	}
	
	public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
		try {
			ResponseEntity<DadosCliente> dadosClienteResponse = clienteResource.clientByCpf(cpf);
			ResponseEntity<List<Cartao>> cartoesClienteResponse = cartoesResource.getCartoesRendaAte(renda);
			
			List<Cartao> cartoes = cartoesClienteResponse.getBody();
			var listaCartoesAprovados = cartoes.stream().map(cartao -> {
				
				DadosCliente dadosCliente = dadosClienteResponse.getBody();
				
				BigDecimal limiteBasico = cartao.getLimiteBasico();
				BigDecimal idadeBd = BigDecimal.valueOf(dadosCliente.getIdade());
				var fator = idadeBd.divide(BigDecimal.valueOf(10));
				BigDecimal limiteAprovado = fator.multiply(limiteBasico);
				
				
				CartaoAprovado aprovado = new CartaoAprovado();
				aprovado.setCartao(cartao.getNome());
				aprovado.setBandeira(cartao.getBandeira());
				aprovado.setLimiteAprovado(limiteAprovado);
				
				return aprovado;
			}).collect(Collectors.toList());
			
			return new RetornoAvaliacaoCliente(listaCartoesAprovados);
			
		}catch(FeignClientException e) {
			int status = e.status();
			if(HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			
			throw new ErroComunicacaoMicroservicesException(e.getMessage(),status);
		}
	}
	
	public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados) {
		try {
			emissaoCartaoPublisher.solicitarCartao(dados);
			var protocolo = UUID.randomUUID().toString();
			return new ProtocoloSolicitacaoCartao(protocolo);
		} catch (Exception e) {
			throw new ErroSolicitacaoCartaoExcepetion(e.getMessage());
		}
	}
}
