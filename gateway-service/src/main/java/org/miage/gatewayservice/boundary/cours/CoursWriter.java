package org.miage.gatewayservice.boundary.cours;

import org.miage.gatewayservice.entity.cours.CoursInput;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.util.Map;

@MessagingGateway
public interface CoursWriter {

    @Gateway(requestChannel = "new-cours")
    void write(CoursInput cours);

    @Gateway(requestChannel = "update-cours")
    void update(Map<String, Object> payload);

    @Gateway(requestChannel = "patch-cours")
    void patch(Map<String, Object> payload);

    @Gateway(requestChannel = "delete-cours")
    void delete(String id);
}