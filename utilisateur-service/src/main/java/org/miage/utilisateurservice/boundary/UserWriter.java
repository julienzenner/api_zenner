package org.miage.utilisateurservice.boundary;

import org.miage.utilisateurservice.entity.UserInput;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface UserWriter {

    @Gateway(requestChannel = "new-user")
    void newUser(UserInput user);
}
