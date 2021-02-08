package org.miage.coursservice.entity.episode;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Service
@AllArgsConstructor
public class EpisodeValidator {

    private final Validator validator;

    public void validate(EpisodeWithLinkInput cours) {
        Set<ConstraintViolation<EpisodeWithLinkInput>> violations =
                validator.validate(cours);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}