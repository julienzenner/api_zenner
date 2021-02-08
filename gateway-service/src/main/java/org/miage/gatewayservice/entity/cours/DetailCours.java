package org.miage.gatewayservice.entity.cours;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.miage.gatewayservice.entity.episode.Episode;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DetailCours extends Cours {

    @JsonInclude(NON_NULL)
    private List<Episode> episodes;

    public DetailCours(Cours cours, List<Episode> episodes) {
        super(cours);
        this.episodes = episodes;
    }
}