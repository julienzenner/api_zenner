package org.miage.gatewayservice.boundary.utilisateur;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface UtilisateurChannel {
    @Output("new-utilisateur")
    MessageChannel outputCreateUtilisateur();

    @Output("register-utilisateur")
    MessageChannel outputRegisterUtilisateur();

    @Output("update-utilisateur")
    MessageChannel outputUpdateUtilisateur();

    @Output("episode-utilisateur")
    MessageChannel outputEpisodeUtilisateur();

    @Output("patch-utilisateur")
    MessageChannel outputPatchUpdateUtilisateur();

    @Output("delete-utilisateur")
    MessageChannel outputDeleteUtilisateur();
}