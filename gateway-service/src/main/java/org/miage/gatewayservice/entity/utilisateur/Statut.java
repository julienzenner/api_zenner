package org.miage.gatewayservice.entity.utilisateur;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Statut {
    ACTIF("Actif"),
    SUPPRIME("Supprimé");

    public final String value;
}