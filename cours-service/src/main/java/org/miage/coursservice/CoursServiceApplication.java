package org.miage.coursservice;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.miage.coursservice.boundary.cours.CoursChannel;
import org.miage.coursservice.boundary.episode.EpisodeChannel;
import org.miage.coursservice.boundary.episode.UtilisateurChannel;
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
@EnableBinding({CoursChannel.class, EpisodeChannel.class, UtilisateurChannel.class})
public class CoursServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoursServiceApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Cours API")
                .version("1.0")
                .description("Documentation Cours API Miage"));
    }
}