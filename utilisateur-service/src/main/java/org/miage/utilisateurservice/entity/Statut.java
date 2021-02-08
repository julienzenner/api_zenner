package org.miage.utilisateurservice.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Statut {
    ACTIF("Actif"),
    SUPPRIME("Supprimé");

    public final String value;
}