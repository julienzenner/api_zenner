package org.miage.gatewayservice.boundary.cours;

import org.miage.gatewayservice.entity.cours.Cours;
import org.miage.gatewayservice.entity.cours.DetailCours;
import org.miage.gatewayservice.entity.episode.Episode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("cours-service")
public interface CoursReader {

    @GetMapping(value = "/cours")
    CollectionModel<EntityModel<Cours>> readAll();

    @GetMapping(value = "/cours/{id}")
    EntityModel<Cours> readOne(@PathVariable String id);

    @GetMapping(value = "/cours/{id}/episodes")
    EntityModel<DetailCours> readEpisodesOfOne(@PathVariable String id);

    @GetMapping(value = "/episodes")
    CollectionModel<EntityModel<Episode>> readAllEpisodes();

    @GetMapping(value = "/episodes/{id}")
    EntityModel<Episode> readOneEpisode(@PathVariable String id);
}