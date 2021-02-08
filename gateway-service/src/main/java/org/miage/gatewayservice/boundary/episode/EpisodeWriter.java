package org.miage.gatewayservice.boundary.episode;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.util.Map;

@MessagingGateway
public interface EpisodeWriter {

    @Gateway(requestChannel = "new-episode")
    void write(Map<String, Object> payload);

    @Gateway(requestChannel = "update-episode")
    void update(Map<String, Object> payload);

    @Gateway(requestChannel = "patch-episode")
    void patch(Map<String, Object> payload);

    @Gateway(requestChannel = "delete-episode")
    void delete(String id);
}