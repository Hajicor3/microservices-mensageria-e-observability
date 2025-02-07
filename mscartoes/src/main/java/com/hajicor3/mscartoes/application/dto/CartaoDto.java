package com.hajicor3.mscartoes.application.dto;

import java.math.BigDecimal;

import com.hajicor3.mscartoes.domain.BandeiraCartoes;
import com.hajicor3.mscartoes.domain.Cartao;

import lombok.Data;

@Data
public class CartaoDto {
	
	private String nome;
	private BandeiraCartoes bandeira;
	private BigDecimal renda;
	private BigDecimal limiteBasico;
	
	public Cartao fromModel() {
		return new Cartao(nome,bandeira,renda,limiteBasico);
	}
}
