package com.microservices.sb_web_mvc;

import org.springframework.boot.builder.SpringApplicationBuilder;

public class MultiServicesRunner {

    public static void main(String[] args) {

        new SpringApplicationBuilder(SbWebMvcApplication.class)
                .properties("server.port=8081")
                .run(args);

        new SpringApplicationBuilder(ServiceNotificationEmail.class)
                .properties("server.port=8082")
                .run(args);

        new SpringApplicationBuilder(ServiceNotificationSms.class)
                .properties("server.port=8083")
                .run(args);
    }
}
