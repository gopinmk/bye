package com.nmk.api.auditlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EntityScan("com.nmk.ibank.service.entity")
@EnableJpaAuditing
public class AuditlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditlogApplication.class, arg);
	}
}
