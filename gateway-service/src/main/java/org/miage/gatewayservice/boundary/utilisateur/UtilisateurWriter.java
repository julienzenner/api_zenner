package org.miage.gatewayservice.boundary.utilisateur;

import org.miage.gatewayservice.entity.utilisateur.UtilisateurWithAccountInput;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.util.Map;

@MessagingGateway
public interface UtilisateurWriter {
    @Gateway(requestChannel = "new-utilisateur")
    void create(UtilisateurWithAccountInput utilisateur);

    @Gateway(requestChannel = "register-utilisateur")
    void registerCours(Map<String, Object> payload);

    @Gateway(requestChannel = "update-utilisateur")
    void update(Map<String, Object> payload);

    @Gateway(requestChannel = "patch-utilisateur")
    void patch(Map<String, Object> payload);

    @Gateway(requestChannel = "delete-utilisateur")
    void delete(String id);
}