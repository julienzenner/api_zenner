package org.miage.utilisateurservice.boundary;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.miage.utilisateurservice.entity.Cours;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CoursClient {

    private final CoursRestClient restClient;

    @HystrixCommand(fallbackMethod = "fallback")
    public Cours get(String id) {
        return restClient.get(id);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public Cours getWithEpisodes(String id) {
        return restClient.getWithEpisodes(id);
    }

    public Cours fallback(String id) {
        return new Cours(id, "non disponible");
    }
}