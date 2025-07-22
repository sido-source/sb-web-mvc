package com.microservices.sb_web_mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ServiceNotificationSms {

	public static void main(String[] args) {
		SpringApplication.run(ServiceNotificationSms.class, args);
	}




	@RestController
	@RequestMapping("/api/v1/notification/sms")
	class NotificationSmsController {

		@GetMapping("/{invoiceId}")
		public String getInvoiceReminder(@PathVariable String invoiceId) {

            // This method will be triggered by the invoice reminder webhook
			return "This is a SMS reminder to pay your invoice nr:" + invoiceId + " . This servcice will trigger next microservices as actions on event";
		}
    }
}


