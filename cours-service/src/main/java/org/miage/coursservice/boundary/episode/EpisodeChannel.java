package org.miage.coursservice.boundary.episode;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface EpisodeChannel {
    @Input("new-episode")
    SubscribableChannel inputNewEpisode();

    @Input("update-episode")
    SubscribableChannel inputUpdateEpisode();

    @Input("patch-episode")
    SubscribableChannel inputPatchEpisode();

    @Input("delete-episode")
    SubscribableChannel inputDeleteEpisode();
}
