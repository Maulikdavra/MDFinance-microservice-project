package com.md.loans;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.md.loans.controller") })
@EnableJpaRepositories("com.md.loans.repository")
@EntityScan("com.md.loans.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Loans microservice REST API Documentation",
				description = "MDFinance Loans microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Maulik Davra",
						email = "maulikdavra06@gmail.com",
						url = "https://www.linkedin.com/in/maulikdavra/"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.linkedin.com/in/maulikdavra/"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "MDFinance Loans microservice REST API Documentation",
				url = "https://www.eazybytes.com/swagger-ui.html"
		)
)
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
