package com.ninja.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages="com.ninja.demo")
public class DemoProgramApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoProgramApplication.class, args);
	}

	@Bean
	public Docket demoApi(){
		return new
				Docket(DocumentationType.SWAGGER_2).select().apis
				(RequestHandlerSelectors.basePackage("com.ninja.demo")).build();
	}


}
