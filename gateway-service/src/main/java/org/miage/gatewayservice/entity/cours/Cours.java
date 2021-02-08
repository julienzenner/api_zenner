package org.miage.gatewayservice.entity.cours;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cours {

    private String id;
    private String titre;
    private String description;
    private Double prix;
    @JsonProperty("episodes_id")
    @JsonInclude(NON_NULL)
    private Set<String> episodesId;

    public Cours(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public Cours(Cours cours) {
        this.id = cours.id;
        this.description = cours.description;
        this.prix = cours.prix;
        this.episodesId = cours.episodesId;
    }
}