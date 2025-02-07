package com.hajizao.clientesms.application.dto;

import com.hajizao.clientesms.model.Client;

import lombok.Data;

@Data
public class ClientDto {

	private String nome;
	private String cpf;
	private Integer idade;
	
	public Client toModel() {
		
		return new Client(nome,cpf,idade);
	}
}
