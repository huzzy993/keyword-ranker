package com.sellics.keywordranker.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.concurrent.*;

@Configuration
public class AppConfig {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}

	@Bean
	public ExecutorService executorService() {
		return Executors.newCachedThreadPool();
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
						.select()
						.apis(RequestHandlerSelectors.any())
						.paths(PathSelectors.any())
						.build();
	}

}
