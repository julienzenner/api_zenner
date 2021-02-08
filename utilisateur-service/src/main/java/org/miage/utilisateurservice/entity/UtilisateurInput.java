package org.miage.utilisateurservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurInput {

    @Email
    @NotNull
    @NotBlank
    private String mail;

    @NotNull
    @NotBlank
    private String nom;

    @NotNull
    @NotBlank
    private String prenom;
}