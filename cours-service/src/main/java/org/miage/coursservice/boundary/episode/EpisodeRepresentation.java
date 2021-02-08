package org.miage.coursservice.boundary.episode;

import lombok.AllArgsConstructor;
import org.miage.coursservice.boundary.cours.CoursRepresentation;
import org.miage.coursservice.boundary.cours.CoursResource;
import org.miage.coursservice.config.UserPrincipal;
import org.miage.coursservice.entity.cours.Cours;
import org.miage.coursservice.entity.episode.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping(value = "/episodes", produces = APPLICATION_JSON_VALUE)
@ExposesResourceFor(Episode.class)
@AllArgsConstructor
public class EpisodeRepresentation {

    private final EpisodeResource resource;
    private final CoursResource coursResource;
    private final EpisodeValidator validator;
    private final FileService fileService;
    private final UtilisateurWriter utilisateurWriter;

    // GET all
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Episode>>> getAllEpisodes() {
        Iterable<Episode> all = resource.findAll();
        return ResponseEntity.ok(episodeToResource(all));
    }

    // GET one
    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<Episode>> getEpisode(@PathVariable String id) {
        return Optional.of(resource.findById(id)).filter(Optional::isPresent)
                .map(e -> {
                    UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getPrincipal();
                    Map<String, String> payload =
                            Map.of("id", userPrincipal.getId(), "episodeId", id);
                    this.utilisateurWriter.episode(payload);
                    return ResponseEntity.ok(episodeToResource(e.get(), true));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // POST
    @PostMapping(value = "/cours/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<URI> save(@PathVariable String id,
                                    @RequestBody @Valid EpisodeWithLinkInput episode) {
        Optional<Cours> body = coursResource.findById(id);
        if (body.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Cours cours = body.get();
        Episode e = new Episode(UUID.randomUUID().toString(),
                episode.getTitre(),
                episode.getVideo());
        Episode saved = resource.save(e);

        cours.getEpisodesId().add(saved.getId());
        coursResource.save(cours);
        URI location = linkTo(CoursRepresentation.class).slash(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/cours/{id}", consumes = MULTIPART_FORM_DATA_VALUE)
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<URI> write(@PathVariable String id,
                                     @RequestParam @Valid EpisodeInput episode,
                                     @RequestParam MultipartFile file) throws IOException {
        Optional<Cours> body = coursResource.findById(id);
        if (body.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Cours cours = body.get();
        Episode e = new Episode(UUID.randomUUID().toString(),
                episode.getTitre(),
                fileService.storeFile(file));
        Episode saved = resource.save(e);

        cours.getEpisodesId().add(saved.getId());
        coursResource.save(cours);
        URI location = linkTo(CoursRepresentation.class).slash(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    // PUT
    @PutMapping(value = "/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateEpisode(@PathVariable String id,
                                              @RequestBody Episode episode) {
        Optional<Episode> body = Optional.ofNullable(episode);
        if (body.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (!resource.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        episode.setId(id);
        resource.save(episode);
        return ResponseEntity.ok().build();
    }

    //PATCH
    @PatchMapping(value = "/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateEpisodePartial(@PathVariable String id,
                                                     @RequestBody Map<Object, Object> fields) {
        Optional<Episode> body = resource.findById(id);
        if (body.isPresent()) {
            Episode episode = body.get();
            fields.forEach((f, v) -> {
                Field field = ReflectionUtils.findField(Episode.class, f.toString());
                field.setAccessible(true);
                ReflectionUtils.setField(field, episode, v);
            });
            validator.validate(new EpisodeWithLinkInput(
                    episode.getTitre(),
                    episode.getVideo()));
            episode.setId(id);
            resource.save(episode);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE
    @DeleteMapping(value = "/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEpisode(@PathVariable String id) {
        Optional<Episode> episode = resource.findById(id);
        episode.ifPresent(e -> {
            Optional<Cours> c = coursResource.findCoursByEpisodeId(e.getId());
            if (c.isPresent()) {
                Cours cours = c.get();
                cours.getEpisodesId().remove(e.getId());
                coursResource.save(cours);
            }
            resource.delete(e);
        });
        return ResponseEntity.noContent().build();
    }

    private CollectionModel<EntityModel<Episode>> episodeToResource(Iterable<Episode> episodes) {
        Link selfLink = linkTo(methodOn(EpisodeRepresentation.class).getAllEpisodes()).withSelfRel();
        List<EntityModel<Episode>> episodeResources = new ArrayList<>();
        episodes.forEach(e -> episodeResources.add(episodeToResource(e, false)));
        return CollectionModel.of(episodeResources, selfLink);
    }

    private EntityModel<Episode> episodeToResource(Episode episode, Boolean collection) {
        Link selfLink = linkTo(EpisodeRepresentation.class).slash(episode.getId()).withSelfRel();
        if (Boolean.TRUE.equals(collection)) {
            Link collectionLink = linkTo(methodOn(EpisodeRepresentation.class).getAllEpisodes())
                    .withRel("collection");
            return EntityModel.of(episode, selfLink, collectionLink);
        } else {
            return EntityModel.of(episode, selfLink);
        }
    }
}