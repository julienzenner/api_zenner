package org.miage.gatewayservice.entity.utilisateur;

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
public class Utilisateur {

    private String id;
    private String mail;
    private String nom;
    private String prenom;
    private Statut statut;
    @JsonProperty("cours_id")
    @JsonInclude(NON_NULL)
    private Set<String> coursId;

    public Utilisateur(Utilisateur utilisateur) {
        this.id = utilisateur.id;
        this.mail = utilisateur.mail;
        this.nom = utilisateur.nom;
        this.prenom = utilisateur.prenom;
        this.statut = utilisateur.statut;
        this.coursId = utilisateur.coursId;
    }

    public Utilisateur(String id, String mail) {
        this.id = id;
        this.mail = mail;
    }
}