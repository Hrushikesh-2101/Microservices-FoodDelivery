package com.foodSystem.Api_Fateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiFatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiFatewayApplication.class, args);
  }
}
