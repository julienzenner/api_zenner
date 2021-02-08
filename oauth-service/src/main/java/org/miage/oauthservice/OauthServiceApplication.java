package org.miage.oauthservice;

import org.miage.oauthservice.boundary.UserChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableDiscoveryClient
@SpringBootApplication
@EnableResourceServer
@EnableBinding(UserChannel.class)
public class OauthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthServiceApplication.class, args);
    }
}