package com.hajicor3.msavaliadorcredito.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hajicor3.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import com.hajicor3.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import com.hajicor3.msavaliadorcredito.application.ex.ErroSolicitacaoCartaoExcepetion;
import com.hajicor3.msavaliadorcredito.domain.model.DadosAvaliacao;
import com.hajicor3.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;
import com.hajicor3.msavaliadorcredito.domain.model.ProtocoloSolicitacaoCartao;
import com.hajicor3.msavaliadorcredito.domain.model.SituacaoCliente;

@RestController
@RequestMapping("/avaliacoes-credito")
public class AvaliadorCreditoController {
	
	@Autowired
	AvaliadorCreditoService service;
	
	@GetMapping
	public String status() {
		return "ok";
	}
	
	@GetMapping(value = "/situacao-cliente", params= "cpf")
	public ResponseEntity consultarSituacaoCliente(@RequestParam String cpf){
		try {
			SituacaoCliente situacaoCliente = service.obterSituacaoCliente(cpf);
			return ResponseEntity.ok(situacaoCliente);
		} catch (DadosClienteNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ErroComunicacaoMicroservicesException e) {
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		
		}
	}
	
	@PostMapping
	public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dados){
		try {
			var avaliacao = service.realizarAvaliacao(dados.getCpf(), dados.getRenda());
			return ResponseEntity.ok(avaliacao);
		}catch(DadosClienteNotFoundException e) {
			return ResponseEntity.notFound().build();
		}catch(ErroComunicacaoMicroservicesException e) {
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	}
	
	@PostMapping("solicitacoes-cartao")
	public ResponseEntity solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados) {
		try {
			ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = service.solicitarEmissaoCartao(dados);
			return ResponseEntity.ok(protocoloSolicitacaoCartao);
			
		} catch (ErroSolicitacaoCartaoExcepetion e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}