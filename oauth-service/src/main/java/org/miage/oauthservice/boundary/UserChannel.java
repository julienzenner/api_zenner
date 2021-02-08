package org.miage.oauthservice.boundary;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface UserChannel {

    @Input("new-user")
    SubscribableChannel inputNewUser();
}