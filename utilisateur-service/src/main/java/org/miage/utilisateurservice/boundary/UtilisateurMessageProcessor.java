package org.miage.utilisateurservice.boundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.miage.utilisateurservice.entity.*;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.*;

import static org.miage.utilisateurservice.entity.Statut.ACTIF;

@Component
@AllArgsConstructor
public class UtilisateurMessageProcessor {

    private final UtilisateurResource resource;
    private final UtilisateurValidator validator;
    private final ObjectMapper mapper;

    @Transactional
    @StreamListener("new-utilisateur")
    @SendTo("new-user")
    public UserInput onMessageNewUtilisateur(Message<UtilisateurWithAccountInput> msg) {
        UtilisateurWithAccountInput utilisateur = msg.getPayload();
        if (!resource.existsByMail(utilisateur.getMail())) {
            Utilisateur u = new Utilisateur(UUID.randomUUID().toString(),
                    utilisateur.getMail(),
                    utilisateur.getNom(),
                    utilisateur.getPrenom(),
                    Collections.emptySet(),
                    Collections.emptySet(),
                    ACTIF);
            Utilisateur saved = this.resource.save(u);
            return new UserInput(
                    saved.getId(),
                    utilisateur.getUsername(),
                    utilisateur.getPassword());
        }
        return null;
    }

    @Transactional
    @StreamListener("register-utilisateur")
    public void onMessageRegisterUtilisateur(Message<Map<String, Object>> msg) {
        String id = (String) msg.getPayload().get("id");
        String coursId = (String) msg.getPayload().get("coursId");
        /*int last = 0;
        if (msg.getPayload().get("carteBancaire") != null) {
            CarteBancaireInput carteBancaire = mapper.convertValue(msg.getPayload().get("carteBancaire"), CarteBancaireInput.class);
            int last = carteBancaire.getNumeroCarteBancaire().charAt(carteBancaire.getNumeroCarteBancaire().length() - 1);
        }*/
        //TODO pb pour récupérer le prix d'un cours
        Optional<Utilisateur> u = this.resource.findById(id);
        u.ifPresent(utilisateur -> {
            utilisateur.getCoursId().add(coursId);
            this.resource.save(utilisateur);
        });
    }

    @Transactional
    @StreamListener("update-utilisateur")
    public void onMessageUpdateUtilisateur(Message<Map<String, Object>> msg) {
        Map<String, Object> payload = msg.getPayload();
        String id = (String) msg.getPayload().get("id");
        Optional<Utilisateur> body =
                Optional.ofNullable(mapper.convertValue(payload.get("utilisateur"), Utilisateur.class));

        if (body.isPresent() && this.resource.existsById(id)) {
            Utilisateur utilisateur = body.get();
            utilisateur.setId(id);
            this.resource.save(utilisateur);
        }
    }

    @Transactional
    @StreamListener("episode-utilisateur")
    public void onMessageEpisodeUtilisateur(Message<Map<String, String>> msg) {
        Map<String, String> payload = msg.getPayload();
        String id = payload.get("id");
        String episodeId = payload.get("episodeId");
        Optional<Utilisateur> body = this.resource.findById(id);
        if (body.isPresent()) {
            Utilisateur utilisateur = body.get();
            utilisateur.getEpisodesVisionnesId().add(episodeId);
            this.resource.save(utilisateur);
        }
    }

    @Transactional
    @StreamListener("patch-utilisateur")
    public void onMessagePatchUtilisateur(Message<Map<String, Object>> msg) {
        String id = (String) msg.getPayload().get("id");
        Map<Object, Object> fields = (HashMap<Object, Object>) msg.getPayload().get("fields");
        Optional<Utilisateur> body = this.resource.findById(id);
        if (body.isPresent()) {
            Utilisateur utilisateur = body.get();
            fields.forEach((f, v) -> {
                Field field = ReflectionUtils.findField(Utilisateur.class, f.toString());
                field.setAccessible(true);
                ReflectionUtils.setField(field, utilisateur, v);
            });
            this.validator.validate(new UtilisateurInput(utilisateur.getMail(), utilisateur.getNom(),
                    utilisateur.getPrenom()));
            utilisateur.setId(id);
            this.resource.save(utilisateur);
        }
    }

    @Transactional
    @StreamListener("delete-utilisateur")
    public void onMessageDeleteUtilisateur(Message<String> msg) {
        String id = msg.getPayload();
        Optional<Utilisateur> utilisateur = this.resource.findByIdAndStatut(id, ACTIF);
        utilisateur.ifPresent(resource::delete);
    }
}