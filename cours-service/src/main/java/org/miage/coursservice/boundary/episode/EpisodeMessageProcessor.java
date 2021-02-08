package org.miage.coursservice.boundary.episode;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.miage.coursservice.boundary.cours.CoursResource;
import org.miage.coursservice.entity.cours.Cours;
import org.miage.coursservice.entity.episode.Episode;
import org.miage.coursservice.entity.episode.EpisodeValidator;
import org.miage.coursservice.entity.episode.EpisodeWithLinkInput;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class EpisodeMessageProcessor {

    private final EpisodeResource resource;
    private final EpisodeValidator validator;
    private final ObjectMapper mapper;
    private final CoursResource coursResource;

    @StreamListener("new-episode")
    @Transactional
    public void onMessageNewEpisode(Message<Map<String, Object>> msg) {
        Map<String, Object> payload = msg.getPayload();
        EpisodeWithLinkInput episode = mapper.convertValue(payload.get("episode"), EpisodeWithLinkInput.class);
        String coursId = (String) payload.get("coursId");

        Optional<Cours> body = coursResource.findById(coursId);
        if (body.isPresent()) {
            Cours cours = body.get();
            Episode e = new Episode(UUID.randomUUID().toString(),
                    episode.getTitre(),
                    episode.getVideo());
            Episode saved = resource.save(e);

            cours.getEpisodesId().add(saved.getId());
            coursResource.save(cours);
        }
    }

    @StreamListener("update-episode")
    @Transactional
    public void onMessageUpdateEpisode(Message<Map<String, Object>> msg) {
        Map<String, Object> payload = msg.getPayload();
        Episode episode = mapper.convertValue(payload.get("episode"), Episode.class);
        String id = (String) payload.get("id");

        Optional<Episode> body = Optional.ofNullable(episode);
        if (body.isPresent() && resource.existsById(id)) {
            episode.setId(id);
            resource.save(episode);
        }
    }

    @StreamListener("patch-episode")
    @Transactional
    public void onMessagePatchEpisode(Message<Map<String, Object>> msg) {
        Map<String, Object> payload = msg.getPayload();
        Map<Object, Object> fields = (Map<Object, Object>) payload.get("fields");
        String id = (String) payload.get("id");

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
        }
    }

    @StreamListener("delete-episode")
    @Transactional
    public void onMessageDeleteEpisode(Message<String> msg) {
        String id = msg.getPayload();
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
    }
}