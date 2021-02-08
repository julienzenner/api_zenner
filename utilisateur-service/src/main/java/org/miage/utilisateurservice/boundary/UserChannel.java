package org.miage.utilisateurservice.boundary;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface UserChannel {

    @Output("new-user")
    MessageChannel outputCreateUser();
}
