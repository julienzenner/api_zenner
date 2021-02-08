package org.miage.coursservice.boundary.episode;

import org.miage.coursservice.entity.episode.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeResource extends JpaRepository<Episode, String> {
}