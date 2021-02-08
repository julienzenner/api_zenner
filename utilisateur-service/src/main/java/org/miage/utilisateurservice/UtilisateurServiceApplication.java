package org.miage.utilisateurservice;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.miage.utilisateurservice.boundary.UserChannel;
import org.miage.utilisateurservice.boundary.UtilisateurChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableBinding({UtilisateurChannel.class, UserChannel.class})
public class UtilisateurServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UtilisateurServiceApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Utilisateur API")
                .version("1.0")
                .description("Documentation Utilisateur API Miage"));
    }
}