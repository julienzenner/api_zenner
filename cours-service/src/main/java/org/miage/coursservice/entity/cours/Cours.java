package org.miage.coursservice.entity.cours;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cours implements Serializable {

    private static final long serialVersionUID = 4684848579948L;

    @Id
    private String id;
    private String titre;
    private String description;
    private Double prix;
    @ElementCollection
    @JsonProperty("episodes_id")
    @JsonInclude(NON_NULL)
    private Set<String> episodesId;

    public Cours(Cours cours) {
        this.id = cours.id;
        this.titre = cours.titre;
        this.description = cours.description;
        this.prix = cours.prix;
        this.episodesId = cours.episodesId;
    }
}