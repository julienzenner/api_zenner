package org.miage.utilisateurservice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Relation(collectionRelation = "utilisateurs")
@Data
@EqualsAndHashCode(callSuper = true)
public class Detail extends Utilisateur {

    private final List<Cours> cours;

    public Detail(Utilisateur utilisateur, List<Cours> cours) {
        super(utilisateur);
        this.cours = cours;
    }
}