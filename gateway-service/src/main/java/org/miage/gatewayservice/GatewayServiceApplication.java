package org.miage.gatewayservice;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.miage.gatewayservice.boundary.cours.CoursChannel;
import org.miage.gatewayservice.boundary.episode.EpisodeChannel;
import org.miage.gatewayservice.boundary.utilisateur.UtilisateurChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;

@EnableDiscoveryClient
@EnableZuulProxy
@EnableFeignClients
@SpringBootApplication
@EnableBinding({CoursChannel.class, UtilisateurChannel.class, EpisodeChannel.class})
@IntegrationComponentScan
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Entry-point API")
                .version("1.0")
                .description("Documentation Entry-point API Miage"));
    }
}