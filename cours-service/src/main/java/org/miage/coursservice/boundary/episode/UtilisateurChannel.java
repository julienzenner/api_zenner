package org.miage.coursservice.boundary.episode;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface UtilisateurChannel {

    @Output("episode-utilisateur")
    MessageChannel outputEpisodeUtilisateur();
}