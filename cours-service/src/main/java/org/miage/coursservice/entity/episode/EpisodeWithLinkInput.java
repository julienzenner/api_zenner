package org.miage.coursservice.entity.episode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeWithLinkInput {

    @NotNull
    @NotBlank
    private String titre;

    @NotNull
    private URL video;
}