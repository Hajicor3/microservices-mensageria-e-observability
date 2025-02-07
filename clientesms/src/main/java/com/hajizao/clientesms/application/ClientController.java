package com.hajizao.clientesms.application;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hajizao.clientesms.application.dto.ClientDto;
import com.hajizao.clientesms.model.Client;


@RestController
@RequestMapping("/clientes")
public class ClientController {
	
	@Autowired
	ClientService service;
	
	@GetMapping
	public String test() {
		return "ok";
	}
	
	@PostMapping
	public ResponseEntity<Client> save(@RequestBody ClientDto clientDto) {
		
		var client = clientDto.toModel();
		service.saveClient(client);
		URI headerLocation =  ServletUriComponentsBuilder.fromCurrentRequest().query("cpf").buildAndExpand(client.getCpf()).toUri();
		
		return ResponseEntity.created(headerLocation).build();
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity clientByCpf(@RequestParam String cpf) {
		var client = service.getByCPF(cpf);
		if(client.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(client);
	}
}
