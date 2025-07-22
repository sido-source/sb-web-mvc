package com.microservices.sb_web_mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SbWebMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbWebMvcApplication.class, args);
	}


	@Configuration
	class RestApiConfig {

		@Bean // declaration bean on the method
		public RestTemplate getRestTemplate(){
			return new RestTemplate();
		}

		@Bean
		public RestTemplate restTemplate(RestTemplateBuilder builder) {

			RestTemplate restTemplate = new RestTemplate();
			// Create SimpleClientHttpRequestFactory with timeouts
			SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
			factory.setConnectTimeout(5000); // ms
			factory.setReadTimeout(10000);   // ms


			// Example: adding interceptors for logging or headers
			List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(restTemplate.getInterceptors());
			//nterceptors.add(new CustomHeaderInterceptor());
			restTemplate.setInterceptors(interceptors);

			restTemplate.setRequestFactory(factory);
			return restTemplate;
		}
	}


	@RestController
	@RequestMapping("/api/v1/invoice-reminder")
	class InvoiceReminderController {

		private final RestTemplate restTemplate;

		public InvoiceReminderController(RestTemplate restTemplate) {
			this.restTemplate = restTemplate;
		}

		@GetMapping("/{invoiceId}")
		public String getInvoiceReminder(@PathVariable String invoiceId) {

			ResponseEntity<String> forEntity = restTemplate.getForEntity(
					"http://localhost:8082/api/v1/notification/email/" + invoiceId,
					String.class
			);

			String responseBody = forEntity.getBody();
			System.out.println("Response from Email Service: " + forEntity);


			// This method will be triggered by the invoice reminder webhook
			return "This is a reminder to pay your invoice nr:" + invoiceId + " . This servcice will trigger next microservices as actions on event";
		}
	}
}


