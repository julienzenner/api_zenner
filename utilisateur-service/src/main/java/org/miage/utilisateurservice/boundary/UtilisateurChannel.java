package org.miage.utilisateurservice.boundary;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface UtilisateurChannel {
    @Input("new-utilisateur")
    SubscribableChannel inputNewUtilisateur();

    @Input("register-utilisateur")
    SubscribableChannel inputRegisterUtilisateur();

    @Input("update-utilisateur")
    SubscribableChannel inputUpdateUtilisateur();

    @Input("episode-utilisateur")
    SubscribableChannel inputEpisodeUtilisateur();

    @Input("patch-utilisateur")
    SubscribableChannel inputPatchUtilisateur();

    @Input("delete-utilisateur")
    SubscribableChannel inputDeleteUtilisateur();
}
