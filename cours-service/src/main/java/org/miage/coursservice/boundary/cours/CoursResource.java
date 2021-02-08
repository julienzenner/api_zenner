package org.miage.coursservice.boundary.cours;

import org.miage.coursservice.entity.cours.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CoursResource extends JpaRepository<Cours, String> {

    @Query("SELECT c from Cours c where :episodeId member of c.episodesId")
    Optional<Cours> findCoursByEpisodeId(String episodeId);
}