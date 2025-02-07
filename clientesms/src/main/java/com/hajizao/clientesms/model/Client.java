package com.hajizao.clientesms.model;

import java.io.Serializable;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EnableDiscoveryClient
public class Client implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private String cpf;
	private Integer idade;
	
	public Client(String nome, String cpf, Integer idade) {
		this.nome = nome;
		this.cpf = cpf;
		this.idade = idade;
	}
	
	
}
