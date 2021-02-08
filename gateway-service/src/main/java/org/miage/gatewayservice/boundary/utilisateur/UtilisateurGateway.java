package org.miage.gatewayservice.boundary.utilisateur;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.miage.gatewayservice.entity.utilisateur.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/utilisateurs", produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UtilisateurGateway {

    public static final String INDISPONIBLE = "indisponible";
    private final UtilisateurWriter utilisateurWriter;
    private final UtilisateurReader utilisateurReader;

    @PreAuthorize("hasRole('ADMIN')")
    @HystrixCommand(fallbackMethod = "fallbackAllUtilisateurs")
    @GetMapping
    public CollectionModel<EntityModel<Utilisateur>> readAllUtilisateurs(
            @RequestParam(value = "statut", required = false) Statut statut) {
        return this.utilisateurReader.readAll(statut);
    }

    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping(value = "/{id}")
    public EntityModel<Utilisateur> readOneUtilisateur(@PathVariable String id) {
        return this.utilisateurReader.readOne(id);
    }

    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    @HystrixCommand(fallbackMethod = "fallbackUtilisateurWithCours")
    @GetMapping(value = "/{id}/cours")
    public EntityModel<DetailUtilisateur> readOneUtilisateurWithCours(
            @PathVariable String id) {
        return this.utilisateurReader.readOneWithCours(id);
    }

    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    @HystrixCommand(fallbackMethod = "fallbackUtilisateurWithCours")
    @GetMapping(value = "/{id}/cours/episodes")
    public EntityModel<DetailUtilisateur> readOneUtilisateurWithCoursAndEpisodes(
            @PathVariable String id) {
        return this.utilisateurReader.readOneWithCoursAndEpisodes(id);
    }

    @PreAuthorize("isAuthenticated() and authentication.principal.id == #id")
    @PostMapping(value = "/{id}/cours/{coursId}")
    public void registerCours(
            @PathVariable String id,
            @PathVariable String coursId,
            @RequestBody(required = false) @Valid CarteBancaireInput carteBancaire
    ) {
        Map<String, Object> payload =
                Map.of("id", id, "coursId", coursId, "carteBancaire", carteBancaire);
        this.utilisateurWriter.registerCours(payload);
    }

    @PreAuthorize("permitAll()")
    @PostMapping
    public void create(@RequestBody @Valid UtilisateurWithAccountInput utilisateur) {
        this.utilisateurWriter.create(utilisateur);
    }

    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    @PutMapping(value = "{id}")
    public void update(@PathVariable String id, @RequestBody Utilisateur utilisateur) {
        Map<String, Object> payload = Map.of("id", id, "utilisateur", utilisateur);
        this.utilisateurWriter.update(payload);
    }

    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    @PatchMapping(value = "/{id}")
    public void updatePartiel(@PathVariable String id, @RequestBody Map<Object, Object> fields) {
        Map<String, Object> payload = Map.of("id", id, "fields", fields);
        this.utilisateurWriter.patch(payload);
    }

    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable String id) {
        this.utilisateurWriter.delete(id);
    }

    public EntityModel<Utilisateur> fallback(String id) {
        Utilisateur u = new Utilisateur(id, INDISPONIBLE);
        return EntityModel.of(u);
    }

    public CollectionModel<EntityModel<Utilisateur>> fallbackAllUtilisateurs(Statut statut) {
        Utilisateur u = new Utilisateur(null, INDISPONIBLE + statut);
        List<EntityModel<Utilisateur>> listeUtilisateur = Collections.singletonList(EntityModel.of(u));
        return CollectionModel.of(listeUtilisateur);
    }

    public EntityModel<DetailUtilisateur> fallbackUtilisateurWithCours(String id) {
        DetailUtilisateur utilisateur =
                new DetailUtilisateur(new Utilisateur(id, INDISPONIBLE), null);
        return EntityModel.of(utilisateur);
    }
}