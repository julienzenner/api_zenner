package org.miage.utilisateurservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cours implements Serializable {

    private static final long serialVersionUID = 1905122041950251207L;

    private String id;
    private String titre;
    private Double prix;
    private String description;
    @JsonProperty("episodes_id")
    @JsonInclude(NON_NULL)
    private Set<String> episodesId;
    @JsonInclude(NON_NULL)
    private List<Episode> episodes;

    public Cours(String id, String titre) {
        this.id = id;
        this.titre = titre;
    }

}