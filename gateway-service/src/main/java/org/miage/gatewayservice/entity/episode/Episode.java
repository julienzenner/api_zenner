package org.miage.gatewayservice.entity.episode;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import java.net.URL;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "episodes")
public class Episode {

    private String id;
    private String titre;
    private URL video;
    @JsonInclude(NON_NULL)
    private Boolean vision;

    public Episode(String id, String titre) {
        this.id = id;
        this.titre = titre;
    }
}
