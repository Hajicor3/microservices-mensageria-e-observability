package com.hajizao.clientesms.infra;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hajizao.clientesms.model.Client;



public interface ClientRepository extends JpaRepository<Client, Long> {
	public Optional<Client>  findByCpf(String cpf);
}
