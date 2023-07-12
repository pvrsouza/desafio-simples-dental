package br.com.test.simplesdental;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimplesDentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimplesDentalApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("1.0.0") String appVersion) {
		return new OpenAPI().info(
				new Info().title("Teste Prático Dev (Back-End Java)").version(appVersion).description("API REST em Java para controle de cadastro de profissionais e seus números de contato"));
	}

}
