package org.miage.gatewayservice.boundary.episode;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface EpisodeChannel {
    @Output("new-episode")
    MessageChannel outputNewEpisode();

    @Output("update-episode")
    MessageChannel outputUpdateEpisode();

    @Output("patch-episode")
    MessageChannel outputPatchEpisode();

    @Output("delete-episode")
    MessageChannel outputDeleteEpisode();
}