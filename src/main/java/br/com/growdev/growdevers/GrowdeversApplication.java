package br.com.growdev.growdevers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication // metadados / decorators
public class GrowdeversApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrowdeversApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http//localhost:3000")
						.allowedMethods("POST", "GET", "PUT", "DELETE");
			}
		}
	}
}

// Spring Web

// Spring MVC

/*
	ESTILO ARQUITETURA
	M = Model
	V = View
	C = Controller
 */
