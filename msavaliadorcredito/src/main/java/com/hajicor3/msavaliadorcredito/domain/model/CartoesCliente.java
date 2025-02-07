package com.hajicor3.msavaliadorcredito.domain.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartoesCliente {
	
	private String nome;
	private String bandeira;
	private BigDecimal limiteLiberado;
}
