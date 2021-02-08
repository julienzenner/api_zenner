package org.miage.utilisateurservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;
}
