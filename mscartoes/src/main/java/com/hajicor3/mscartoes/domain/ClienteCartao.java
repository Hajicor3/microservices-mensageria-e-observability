package com.hajicor3.mscartoes.domain;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ClienteCartao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cpf;
	@ManyToOne
	@JoinColumn(name = "id_cartao")
	private Cartao cartao;
	private BigDecimal limite;
	
	public ClienteCartao(String cpf, Cartao cartao, BigDecimal limite) {
		this.cpf = cpf;
		this.cartao = cartao;
		this.limite = limite;
	}
}
