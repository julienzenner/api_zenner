package org.miage.utilisateurservice.boundary;

import org.miage.utilisateurservice.entity.Cours;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cours-service")
public interface CoursRestClient {

    @GetMapping("/cours/{id}")
    Cours get(@PathVariable String id);

    @GetMapping("/cours/{id}/episodes")
    Cours getWithEpisodes(@PathVariable String id);
}