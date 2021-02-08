package org.miage.utilisateurservice.boundary;

import lombok.AllArgsConstructor;
import org.miage.utilisateurservice.entity.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNullElse;
import static org.miage.utilisateurservice.entity.Statut.ACTIF;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/utilisateurs", produces = APPLICATION_JSON_VALUE)
@ExposesResourceFor(Utilisateur.class)
@AllArgsConstructor
public class UtilisateurRepresentation {

    private final CoursClient coursClient;
    private final UtilisateurResource resource;
    private final UtilisateurValidator validator;
    private final UserWriter userWriter;

    // GET all
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CollectionModel<EntityModel<Utilisateur>>> getAllUtilisateurs(
            @RequestParam(value = "statut", required = false) Statut statut) {
        Iterable<Utilisateur> all = resource.findAllByStatut(requireNonNullElse(statut, ACTIF));
        return ResponseEntity.ok(utilisateurToResource(all, statut));
    }

    // GET one
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    public ResponseEntity<EntityModel<Utilisateur>> getUtilisateur(@PathVariable String id) {
        return Optional.of(resource.findById(id)).filter(Optional::isPresent)
                .map(u -> ResponseEntity.ok(utilisateurToResource(u.get(), true)))
                .orElse(ResponseEntity.notFound().build());
    }

    // GET cours of one
    @GetMapping(value = "/{id}/cours")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    public ResponseEntity<EntityModel<Utilisateur>> getUtilisateurWithCours(
            @PathVariable String id) {
        return Optional.of(resource.findById(id)).filter(Optional::isPresent)
                .map(u -> {
                    List<Cours> cours = u.get()
                            .getCoursId()
                            .stream()
                            .map(coursClient::get)
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(utilisateurToResource(new Detail(u.get(), cours), true));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // GET cours and episodes of one
    @GetMapping(value = "/{id}/cours/episodes")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    public ResponseEntity<EntityModel<Utilisateur>> getUtilisateurWithCoursAndEpisodes(
            @PathVariable String id) {
        return Optional.of(resource.findById(id)).filter(Optional::isPresent)
                .map(u -> {
                    List<Cours> cours = u.get()
                            .getCoursId()
                            .stream()
                            .map(coursClient::getWithEpisodes)
                            .collect(Collectors.toList());

                    cours.forEach(c -> c.getEpisodes().forEach(episode -> {
                        episode.setVision(false);
                        if (u.get().getEpisodesVisionnesId().contains(episode.getId())) {
                            episode.setVision(true);
                        }
                    }));
                    return ResponseEntity.ok(utilisateurToResource(new Detail(u.get(), cours), true));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/{id}/cours/{coursId}")
    @PreAuthorize("authentication.principal.id == #id")
    public ResponseEntity<URI> registerCours(
            @PathVariable String id,
            @PathVariable String coursId,
            @RequestBody(required = false) @Valid CarteBancaireInput carteBancaire
    ) {
        Cours cours = coursClient.get(coursId);
        if (cours.getPrix() != 0.0 && carteBancaire == null) {
            return ResponseEntity.badRequest().build();
        }
        if (cours.getPrix() > 0.0 && carteBancaire != null) {
            int last = carteBancaire
                    .getNumeroCarteBancaire()
                    .charAt(carteBancaire.getNumeroCarteBancaire().length() - 1);
            if (cours.getPrix() != 0.0 && last % 2 != 0) {
                return ResponseEntity.badRequest().build();
            }
        }
        Optional<Utilisateur> u = this.resource.findById(id);
        if (u.isPresent()) {
            Utilisateur utilisateur = u.get();
            utilisateur.getCoursId().add(coursId);
            Utilisateur saved = resource.save(utilisateur);
            URI location = linkTo(UtilisateurRepresentation.class).slash(saved.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
        return ResponseEntity.badRequest().build();
    }

    // POST
    @PostMapping
    @Transactional
    @PreAuthorize("permitAll()")
    public ResponseEntity<URI> save(@RequestBody @Valid UtilisateurWithAccountInput utilisateur) {
        if (resource.existsByMail(utilisateur.getMail())) {
            return ResponseEntity.badRequest().build();
        }
        Utilisateur u = new Utilisateur(UUID.randomUUID().toString(),
                utilisateur.getMail(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                Collections.emptySet(),
                Collections.emptySet(),
                ACTIF);
        Utilisateur saved = resource.save(u);
        UserInput user = new UserInput(
                saved.getId(),
                utilisateur.getUsername(),
                utilisateur.getPassword());
        userWriter.newUser(user);
        URI location = linkTo(UtilisateurRepresentation.class).slash(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    // PUT
    @PutMapping(value = "/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    public ResponseEntity<Void> updateUtilisateur(
            @PathVariable String id,
            @RequestBody Utilisateur utilisateur
    ) {
        Optional<Utilisateur> body = Optional.ofNullable(utilisateur);
        if (body.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (!resource.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        utilisateur.setId(id);
        resource.save(utilisateur);
        return ResponseEntity.ok().build();
    }

    //PATCH
    @PatchMapping(value = "/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    public ResponseEntity<Void> updateUtilisatteurPartial(
            @PathVariable String id,
            @RequestBody Map<Object, Object> fields
    ) {
        Optional<Utilisateur> body = resource.findById(id);
        if (body.isPresent()) {
            Utilisateur utilisateur = body.get();
            fields.forEach((f, v) -> {
                Field field = ReflectionUtils.findField(Episode.class, f.toString());
                field.setAccessible(true);
                ReflectionUtils.setField(field, utilisateur, v);
            });
            validator.validate(new UtilisateurInput(utilisateur.getMail(), utilisateur.getNom(),
                    utilisateur.getPrenom()));
            utilisateur.setId(id);
            resource.save(utilisateur);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE
    @DeleteMapping(value = "/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable String id) {
        Optional<Utilisateur> utilisateur = resource.findById(id);
        utilisateur.ifPresent(resource::delete);
        return ResponseEntity.noContent().build();
    }

    private CollectionModel<EntityModel<Utilisateur>> utilisateurToResource(Iterable<Utilisateur> utilisateurs,
                                                                            Statut statut) {
        Link selfLink = linkTo(methodOn(UtilisateurRepresentation.class)
                .getAllUtilisateurs(statut))
                .withSelfRel();
        List<EntityModel<Utilisateur>> utilisateurResources = new ArrayList<>();
        utilisateurs.forEach(utilisateur ->
                utilisateurResources.add(utilisateurToResource(utilisateur, false)));
        return CollectionModel.of(utilisateurResources, selfLink);
    }

    private EntityModel<Utilisateur> utilisateurToResource(Utilisateur utilisateur, Boolean collection) {
        Link selfLink = linkTo(UtilisateurRepresentation.class).slash(utilisateur.getId()).withSelfRel();
        if (Boolean.TRUE.equals(collection)) {
            Link collectionLink = linkTo(methodOn(UtilisateurRepresentation.class)
                    .getAllUtilisateurs(null))
                    .withRel("collection");
            return EntityModel.of(utilisateur, selfLink, collectionLink);
        } else {
            return EntityModel.of(utilisateur, selfLink);
        }
    }
}