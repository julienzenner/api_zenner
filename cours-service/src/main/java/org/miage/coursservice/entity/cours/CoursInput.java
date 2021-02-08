package org.miage.coursservice.entity.cours;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursInput {

    @NotNull
    @NotBlank
    private String titre;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @DecimalMin("0.0")
    private Double prix;
}