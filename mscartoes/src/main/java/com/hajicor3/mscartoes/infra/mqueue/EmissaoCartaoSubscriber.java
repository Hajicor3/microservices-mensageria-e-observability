package com.hajicor3.mscartoes.infra.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hajicor3.mscartoes.domain.Cartao;
import com.hajicor3.mscartoes.domain.ClienteCartao;
import com.hajicor3.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import com.hajicor3.mscartoes.infra.repository.CartaoRepository;
import com.hajicor3.mscartoes.infra.repository.ClienteCartaoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartaoSubscriber {
	
	private final CartaoRepository cartaoRepository;
	private final ClienteCartaoRepository clienteCartaoRepository;
	
	@RabbitListener(queues = "${mq.queues.emissoes-cartao}")
	public void receberSolicitacaoEmissao(@Payload String payload) {
		try {
			var mapper = new ObjectMapper();
			DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
			Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();
			
			ClienteCartao clienteCartao = new ClienteCartao(dados.getCpf(),cartao,dados.getLimiteLiberado());
			clienteCartaoRepository.save(clienteCartao);
			
		} catch (Exception e) {
			log.error("Erro ao receber solicitacao de emissao de cartao: {} ", e.getMessage());
		}
	}
}
