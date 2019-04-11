package com.chintoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableJpaRepositories
@ComponentScan("com.*")
@EntityScan("com.*")
@EnableAutoConfiguration
//@EnableDiscoveryClient
@EnableScheduling
public class ChintooApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChintooApplication.class, args);
	}
}
