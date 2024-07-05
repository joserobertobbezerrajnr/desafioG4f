package com.bancojose.cartoes_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CartoesMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartoesMsApplication.class, args);
    }
}
