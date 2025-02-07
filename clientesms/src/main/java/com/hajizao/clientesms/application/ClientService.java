package com.hajizao.clientesms.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hajizao.clientesms.infra.ClientRepository;
import com.hajizao.clientesms.model.Client;


@Service
public class ClientService {
	
	@Autowired
	ClientRepository repository;
	
	public Client saveClient(Client client) {
		return repository.save(client);
	}
	
	public Optional<Client> getByCPF(String cpf) {
		return repository.findByCpf(cpf);
	}
}
