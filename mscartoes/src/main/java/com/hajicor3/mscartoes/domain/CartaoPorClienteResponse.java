package com.hajicor3.mscartoes.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaoPorClienteResponse {
	
	private String nome;
	private String bandeira;
	private BigDecimal limiteLiberado;
	
	public static CartaoPorClienteResponse fromModel(ClienteCartao model) {
		
		return new CartaoPorClienteResponse(
				model.getCartao().getNome(),
				model.getCartao().getBandeira().toString(),
				model.getLimite()
				);
	}
}
