package com.hajicor3.mscartoes.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hajicor3.mscartoes.application.dto.CartaoDto;
import com.hajicor3.mscartoes.domain.Cartao;
import com.hajicor3.mscartoes.domain.CartaoPorClienteResponse;
import com.hajicor3.mscartoes.domain.ClienteCartao;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {
	
	@Autowired
	private CartaoService cartaoService;
	@Autowired
	private ClienteCartaoService ClienteCartaoService;
	
	@GetMapping
	public String teste() {
		return "ok";
	}
	
	@PostMapping
	public ResponseEntity<Cartao> saveCartao(@RequestBody CartaoDto cartaoDto) {
		
		var cartao = cartaoDto.fromModel();
		cartaoService.salvarCartao(cartao);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(params = "renda")
	public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam Long renda){
		
		List<Cartao> cartoes = cartaoService.getCartoesRendaMenorIgual(renda);
		return ResponseEntity.ok(cartoes);
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity<List<CartaoPorClienteResponse>> listaCartoesByCliente(@RequestParam String cpf){
		List<ClienteCartao> lista = ClienteCartaoService.listaCartoesByCpf(cpf);
		List<CartaoPorClienteResponse> resultList = lista.stream().map(CartaoPorClienteResponse::fromModel).collect(Collectors.toList());
		return ResponseEntity.ok(resultList);
	}
	
	@GetMapping(params = "id")
	public ResponseEntity<Cartao> cartaoPorId(@RequestParam Long id){
		var cartao = cartaoService.cartaoById(id);
		return ResponseEntity.ok().body(cartao);
	}
}
