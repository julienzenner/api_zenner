package org.miage.gatewayservice.boundary.episode;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.miage.gatewayservice.boundary.FileService;
import org.miage.gatewayservice.boundary.cours.CoursReader;
import org.miage.gatewayservice.entity.episode.Episode;
import org.miage.gatewayservice.entity.episode.EpisodeInput;
import org.miage.gatewayservice.entity.episode.EpisodeWithLinkInput;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping(value = "/episodes", produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class EpisodeGateway {

    public static final String INDISPONIBLE = "indisponible";
    public static final String EPISODE = "episode";
    private final CoursReader episodeReader;
    private final EpisodeWriter episodeWriter;
    private final FileService fileService;

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping
    public CollectionModel<EntityModel<Episode>> getAll() {
        return this.episodeReader.readAllEpisodes();
    }

    @HystrixCommand(fallbackMethod = "fallbackOne")
    @GetMapping(value = "/{id}")
    public EntityModel<Episode> getOne(@PathVariable String id) {
        return this.episodeReader.readOneEpisode(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/cours/{id}")
    public void write(@PathVariable String id,
                      @RequestBody @Valid EpisodeWithLinkInput episode) {
        Map<String, Object> payload = Map.of("coursId", id, EPISODE, episode);
        this.episodeWriter.write(payload);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/cours/{id}", consumes = {MULTIPART_FORM_DATA_VALUE})
    public void write(@PathVariable String id,
                      @RequestParam @Valid EpisodeInput episode,
                      @RequestParam MultipartFile file) throws IOException {
        episode.setVideo(fileService.storeFile(file));
        Map<String, Object> payload = Map.of("coursId", id, EPISODE, episode);
        this.episodeWriter.write(payload);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public void update(@PathVariable String id, @RequestBody Episode episode) {
        Map<String, Object> payload =
                Map.of(EPISODE, episode, "id", id);
        this.episodeWriter.update(payload);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "/{id}")
    public void patch(@PathVariable String id, @RequestBody Map<Object, Object> fields) {
        Map<String, Object> payload = Map.of("id", id, "fields", fields);
        this.episodeWriter.patch(payload);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id) {
        this.episodeWriter.delete(id);
    }

    public CollectionModel<EntityModel<Episode>> fallback() {
        Episode e = new Episode(null, INDISPONIBLE);
        List<EntityModel<Episode>> listeEpisode = Collections.singletonList(EntityModel.of(e));
        return CollectionModel.of(listeEpisode);
    }

    public EntityModel<Episode> fallbackOne(String id) {
        Episode e = new Episode(id, INDISPONIBLE);
        return EntityModel.of(e);
    }

    public EntityModel<Episode> fallbackOne(String id, String utilisateurId) {
        Episode e = new Episode(id, INDISPONIBLE + utilisateurId);
        return EntityModel.of(e);
    }
}