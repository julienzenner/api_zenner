package org.miage.coursservice.boundary.cours;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CoursChannel {

    @Input("new-cours")
    SubscribableChannel inputNewCours();

    @Input("update-cours")
    SubscribableChannel inputUpdateCours();

    @Input("patch-cours")
    SubscribableChannel inputPatchCours();

    @Input("delete-cours")
    SubscribableChannel inputDeleteCours();
}