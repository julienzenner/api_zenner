package org.miage.gatewayservice.boundary.utilisateur;

import org.miage.gatewayservice.entity.utilisateur.DetailUtilisateur;
import org.miage.gatewayservice.entity.utilisateur.Statut;
import org.miage.gatewayservice.entity.utilisateur.Utilisateur;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("utilisateur-service")
public interface UtilisateurReader {

    @GetMapping(value = "/utilisateurs")
    CollectionModel<EntityModel<Utilisateur>> readAll(@RequestParam(value = "statut", required = false) Statut statut);

    @GetMapping(value = "/utilisateurs/{id}")
    EntityModel<Utilisateur> readOne(@PathVariable String id);

    @GetMapping(value = "/utilisateurs/{id}/cours")
    EntityModel<DetailUtilisateur> readOneWithCours(@PathVariable String id);

    @GetMapping(value = "/utilisateurs/{id}/cours/episodes")
    EntityModel<DetailUtilisateur> readOneWithCoursAndEpisodes(@PathVariable String id);
}