package org.miage.gatewayservice.boundary.cours;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.miage.gatewayservice.entity.cours.Cours;
import org.miage.gatewayservice.entity.cours.CoursInput;
import org.miage.gatewayservice.entity.cours.DetailCours;
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
@RequestMapping(value = "/cours", produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CoursGateway {

    public static final String INDISPONIBLE = "indisponible";
    private final CoursReader coursReader;
    private final CoursWriter coursWriter;

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping
    public CollectionModel<EntityModel<Cours>> getAll() {
        return this.coursReader.readAll();
    }

    @HystrixCommand(fallbackMethod = "fallbackOne")
    @GetMapping(value = "/{id}")
    public EntityModel<Cours> getOne(@PathVariable String id) {
        return this.coursReader.readOne(id);
    }

    @HystrixCommand(fallbackMethod = "fallbackEpisodes")
    @GetMapping(value = "/{id}/episodes")
    public EntityModel<DetailCours> getEpisodesOfCours(@PathVariable String id) {
        return this.coursReader.readEpisodesOfOne(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void write(@RequestBody @Valid CoursInput cours) {
        this.coursWriter.write(cours);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public void update(@RequestBody Cours cours, @PathVariable String id) {
        Map<String, Object> payload =
                Map.of("cours", cours, "id", id);
        this.coursWriter.update(payload);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "/{id}")
    public void patch(@PathVariable String id,
                      @RequestBody Map<Object, Object> fields) {
        Map<String, Object> payload =
                Map.of("fields", fields, "id", id);
        this.coursWriter.patch(payload);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id) {
        this.coursWriter.delete(id);
    }

    public CollectionModel<EntityModel<Cours>> fallback() {
        Cours c = new Cours(null, INDISPONIBLE);
        List<EntityModel<Cours>> listeCours = Collections.singletonList(EntityModel.of(c));
        return CollectionModel.of(listeCours);
    }

    public EntityModel<Cours> fallbackOne(String id) {
        Cours c = new Cours(id, INDISPONIBLE);
        return EntityModel.of(c);
    }

    public EntityModel<DetailCours> fallbackEpisodes(String id) {
        DetailCours c = new DetailCours(new Cours(id, INDISPONIBLE), null);
        return EntityModel.of(c);
    }
}