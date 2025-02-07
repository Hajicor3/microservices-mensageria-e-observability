package com.hajicor3.msavaliadorcredito.infra.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hajicor3.msavaliadorcredito.domain.model.Cartao;
import com.hajicor3.msavaliadorcredito.domain.model.CartoesCliente;

@FeignClient(value = "mscartoes", path = "/cartoes")
public interface CartoesResourceClient {
	
	@GetMapping(params = "cpf")
	ResponseEntity<List<CartoesCliente>> listaCartoesByCliente(@RequestParam String cpf);
	
	@GetMapping(params = "renda")
	public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam Long renda);
}
