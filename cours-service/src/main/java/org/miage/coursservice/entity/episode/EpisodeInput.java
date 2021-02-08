package org.miage.coursservice.entity.episode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeInput {

    @NotNull
    @NotBlank
    private String titre;
}
