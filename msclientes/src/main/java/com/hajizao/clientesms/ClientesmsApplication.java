package com.hajizao.clientesms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ClientesmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientesmsApplication.class, args);
	}

}
