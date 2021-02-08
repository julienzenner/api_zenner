package org.miage.gatewayservice.boundary.cours;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CoursChannel {
    @Output("new-cours")
    MessageChannel outputNewCours();

    @Output("update-cours")
    MessageChannel outputUpdateCours();

    @Output("patch-cours")
    MessageChannel outputPatchCours();

    @Output("delete-cours")
    MessageChannel outputDeleteCours();
}