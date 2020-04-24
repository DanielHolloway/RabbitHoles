package com.rabbitholes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
// import org.springframework.context.annotation.ComponentScan;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// @Autowire
// @Configuration
// @ComponentScan("com.rabbitholes.*")
// //@EnableJpaRepositories(basePackages = {"com.rabbitholes.*"})
// @EntityScan("com.rabbitholes.*")
// @EnableAutoConfiguration
@SpringBootApplication(scanBasePackages= {"com.rabbitholes"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}
