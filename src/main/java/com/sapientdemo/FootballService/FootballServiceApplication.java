package com.sapientdemo.FootballService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
@EnableSwagger2
public class FootballServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootballServiceApplication.class, args);
	}

}
