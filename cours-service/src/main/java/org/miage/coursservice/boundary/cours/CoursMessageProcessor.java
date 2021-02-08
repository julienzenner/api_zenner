package org.miage.coursservice.boundary.cours;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.miage.coursservice.boundary.episode.EpisodeResource;
import org.miage.coursservice.entity.cours.Cours;
import org.miage.coursservice.entity.cours.CoursInput;
import org.miage.coursservice.entity.cours.CoursValidator;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class CoursMessageProcessor {

    private final CoursResource resource;
    private final CoursValidator validator;
    private final ObjectMapper mapper;
    private final EpisodeResource episodeResource;

    @StreamListener("new-cours")
    @Transactional
    public void onMessageNewCours(Message<CoursInput> msg) {
        CoursInput cours = msg.getPayload();
        Cours c = new Cours(UUID.randomUUID().toString(),
                cours.getTitre(),
                cours.getDescription(),
                cours.getPrix(),
                Collections.emptySet());
        resource.save(c);
    }

    @StreamListener("update-cours")
    @Transactional
    public void onMessageUpdateCours(Message<Map<String, Object>> msg) {
        Map<String, Object> payload = msg.getPayload();
        Cours cours = mapper.convertValue(payload.get("cours"), Cours.class);
        String id = (String) payload.get("id");

        Optional<Cours> body = Optional.ofNullable(cours);
        if (body.isPresent() && resource.existsById(id)) {
            cours.setId(id);
            resource.save(cours);
        }
    }

    @StreamListener("patch-cours")
    @Transactional
    public void onMessagePatchCours(Message<Map<String, Object>> msg) {
        Map<String, Object> payload = msg.getPayload();
        Map<Object, Object> fields = (Map<Object, Object>) payload.get("fields");
        String id = (String) payload.get("id");

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
        }
    }

    @StreamListener("delete-cours")
    @Transactional
    public void onMessageDeleteCours(Message<String> msg) {
        String id = msg.getPayload();
        Optional<Cours> cours = resource.findById(id);
        cours.ifPresent(c -> {
            c.getEpisodesId().forEach(episodeResource::deleteById);
            resource.delete(c);
        });
        cours.ifPresent(resource::delete);
    }
}