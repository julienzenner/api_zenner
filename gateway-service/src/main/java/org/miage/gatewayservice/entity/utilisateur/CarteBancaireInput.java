package org.miage.gatewayservice.entity.utilisateur;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarteBancaireInput {

    @NotNull
    @NotBlank
    @Pattern(regexp = "^4[0-9]{12}(?:[0-9]{3})?$|^5[1-5][0-9]{14}$")
    @JsonProperty(value = "numero_carte_bancaire")
    private String numeroCarteBancaire;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]{3,4}$")
    private String cvv;
    @NotNull
    @NotBlank
    @JsonProperty(value = "code_validation")
    private String codeValidation;
}