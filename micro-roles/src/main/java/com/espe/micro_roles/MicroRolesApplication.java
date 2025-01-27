package com.espe.micro_roles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroRolesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroRolesApplication.class, args);
	}

}
