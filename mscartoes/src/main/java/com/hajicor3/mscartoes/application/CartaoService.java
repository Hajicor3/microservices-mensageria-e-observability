package com.hajicor3.mscartoes.application;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hajicor3.mscartoes.domain.Cartao;
import com.hajicor3.mscartoes.infra.repository.CartaoRepository;

@Service
public class CartaoService {
	
	@Autowired
	private CartaoRepository repository;
	
	@Transactional
	public Cartao salvarCartao(Cartao cartoes) {
		return repository.save(cartoes);
	}
	
	@Transactional
	public List<Cartao> getCartoesRendaMenorIgual(Long renda){
		
		var rendaBigDecimal = BigDecimal.valueOf(renda);
		return repository.findByRendaLessThanEqual(rendaBigDecimal);
	}
	
	@Transactional
	public Cartao cartaoById(Long id) {
		var cartao = repository.findById(id).get();
		return cartao;
	}
}
