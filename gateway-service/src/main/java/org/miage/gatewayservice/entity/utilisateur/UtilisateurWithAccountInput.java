package org.miage.gatewayservice.entity.utilisateur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurWithAccountInput {

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
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;
}