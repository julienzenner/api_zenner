package org.miage.gatewayservice.entity.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.miage.gatewayservice.entity.episode.EpisodeInput;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StringToEpisodeInputConverter implements Converter<String, EpisodeInput> {

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public EpisodeInput convert(String source) {
        return objectMapper.readValue(source, EpisodeInput.class);
    }
}