package org.miage.utilisateurservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@SQLDelete(sql = "update utilisateur set statut='SUPPRIME' where id=?")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 3768795698008L;

    @Id
    private String id;
    @Column(unique = true)
    private String mail;
    private String nom;
    private String prenom;
    @ElementCollection
    @JsonProperty("cours_id")
    @JsonInclude(NON_NULL)
    private Set<String> coursId;
    @ElementCollection
    @JsonProperty("episodes_visionnes_id")
    @JsonInclude(NON_NULL)
    private Set<String> episodesVisionnesId;
    @Enumerated(EnumType.STRING)
    private Statut statut;

    public Utilisateur(Utilisateur utilisateur) {
        this.id = utilisateur.id;
        this.mail = utilisateur.mail;
        this.nom = utilisateur.nom;
        this.prenom = utilisateur.prenom;
        this.coursId = utilisateur.coursId;
        this.episodesVisionnesId = utilisateur.episodesVisionnesId;
        this.statut = utilisateur.statut;
    }
}