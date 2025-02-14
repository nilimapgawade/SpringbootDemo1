package com.collections.genesys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories(basePackages = { "com.collections.genesys.repository" })
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class CollectionsGenesysApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollectionsGenesysApplication.class, args);
	}

}
