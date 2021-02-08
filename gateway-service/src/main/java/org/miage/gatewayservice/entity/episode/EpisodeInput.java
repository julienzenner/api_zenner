package org.miage.gatewayservice.entity.episode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeInput {

    @NotNull
    @NotBlank
    private String titre;

    private URL video;
}