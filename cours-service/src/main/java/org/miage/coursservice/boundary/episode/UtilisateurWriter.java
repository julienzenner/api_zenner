package org.miage.coursservice.boundary.episode;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.util.Map;

@MessagingGateway
public interface UtilisateurWriter {

    @Gateway(requestChannel = "episode-utilisateur")
    void episode(Map<String, String> payload);
}