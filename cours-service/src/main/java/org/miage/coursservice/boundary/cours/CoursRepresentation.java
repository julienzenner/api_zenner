package org.miage.coursservice.boundary.cours;

import lombok.AllArgsConstructor;
import org.miage.coursservice.boundary.episode.EpisodeResource;
import org.miage.coursservice.entity.cours.Cours;
import org.miage.coursservice.entity.cours.CoursInput;
import org.miage.coursservice.entity.cours.CoursValidator;
import org.miage.coursservice.entity.cours.Detail;
import org.miage.coursservice.entity.episode.Episode;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/cours", produces = APPLICATION_JSON_VALUE)
@ExposesResourceFor(Cours.class)
@AllArgsConstructor
public class CoursRepresentation {

    private final CoursResource resource;
    private final EpisodeResource episodeResource;
    private final CoursValidator validator;

    // GET all
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Cours>>> getAllCours() {
        Iterable<Cours> all = resource.findAll();
        return ResponseEntity.ok(coursToResource(all));
    }

    // GET one
    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<Cours>> getCours(@PathVariable String id) {
        return Optional.of(resource.findById(id)).filter(Optional::isPresent)
                .map(c -> ResponseEntity.ok(coursToResource(c.get(), true)))
                .orElse(ResponseEntity.notFound().build());
    }

    // GET episodes of one
    @GetMapping(value = "/{id}/episodes")
    public ResponseEntity<EntityModel<Cours>> getEpisodesOfCours(@PathVariable String id) {
        return Optional.of(resource.findById(id)).filter(Optional::isPresent)
                .map(c -> {
                    List<Episode> episodes = c.get()
                            .getEpisodesId()
                            .stream()
                            .map(episodeResource::findById)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(coursToResource(new Detail(c.get(), episodes), true));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // POST
    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<URI> save(@RequestBody @Valid CoursInput cours) {
        Cours c = new Cours(UUID.randomUUID().toString(),
                cours.getTitre(),
                cours.getDescription(),
                cours.getPrix(),
                Collections.emptySet());
        Cours saved = resource.save(c);
        URI location = linkTo(CoursRepresentation.class).slash(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    // PUT
    @PutMapping(value = "/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateCours(@PathVariable String id,
                                            @RequestBody Cours cours) {
        Optional<Cours> body = Optional.ofNullable(cours);
        if (body.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (!resource.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cours.setId(id);
        resource.save(cours);
        return ResponseEntity.ok().build();
    }

    //PATCH
    @PatchMapping(value = "/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateCoursPartial(@PathVariable String id,
                                                   @RequestBody Map<Object, Object> fields) {
        Optional<Cours> body = resource.findById(id);
        if (body.isPresent()) {
            Cours cours = body.get();
            fields.forEach((f, v) -> {
                Field field = ReflectionUtils.findField(Cours.class, f.toString());
                field.setAccessible(true);
                ReflectionUtils.setField(field, cours, v);
            });
            validator.validate(new CoursInput(cours.getTitre(), cours.getDescription(), cours.getPrix()));
            cours.setId(id);
            resource.save(cours);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE
    @DeleteMapping(value = "/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCours(@PathVariable String id) {
        Optional<Cours> cours = resource.findById(id);
        cours.ifPresent(c -> {
            c.getEpisodesId().forEach(episodeResource::deleteById);
            resource.delete(c);
        });
        return ResponseEntity.noContent().build();
    }

    private CollectionModel<EntityModel<Cours>> coursToResource(Iterable<Cours> cours) {
        Link selfLink = linkTo(methodOn(CoursRepresentation.class).getAllCours()).withSelfRel();
        List<EntityModel<Cours>> coursResources = new ArrayList<>();
        cours.forEach(c -> coursResources.add(coursToResource(c, false)));
        return CollectionModel.of(coursResources, selfLink);
    }

    private EntityModel<Cours> coursToResource(Cours cours, Boolean collection) {
        Link selfLink = linkTo(CoursRepresentation.class).slash(cours.getId()).withSelfRel();
        if (Boolean.TRUE.equals(collection)) {
            Link collectionLink = linkTo(methodOn(CoursRepresentation.class).getAllCours())
                    .withRel("collection");
            return EntityModel.of(cours, selfLink, collectionLink);
        } else {
            return EntityModel.of(cours, selfLink);
        }
    }
}