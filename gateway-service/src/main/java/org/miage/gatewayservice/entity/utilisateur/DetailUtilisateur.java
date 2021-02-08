package org.miage.gatewayservice.entity.utilisateur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.miage.gatewayservice.entity.cours.DetailCours;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DetailUtilisateur extends Utilisateur {

    private List<DetailCours> cours;

    public DetailUtilisateur(Utilisateur utilisateur, List<DetailCours> cours) {
        super(utilisateur);
        this.cours = cours;
    }
}