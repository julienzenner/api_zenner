package org.miage.coursservice.entity.cours;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.miage.coursservice.entity.episode.Episode;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Detail extends Cours {

    private List<Episode> episodes;

    public Detail(Cours cours, List<Episode> episodes) {
        super(cours);
        this.episodes = episodes;
    }
}