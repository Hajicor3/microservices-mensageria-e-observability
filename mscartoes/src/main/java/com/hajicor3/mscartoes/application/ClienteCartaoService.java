package com.hajicor3.mscartoes.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hajicor3.mscartoes.domain.ClienteCartao;
import com.hajicor3.mscartoes.infra.repository.ClienteCartaoRepository;

@Service
public class ClienteCartaoService {
	
	@Autowired
	private ClienteCartaoRepository repository;
	
	public List<ClienteCartao> listaCartoesByCpf(String cpf){
		return repository.findBycpf(cpf);
	}
}
