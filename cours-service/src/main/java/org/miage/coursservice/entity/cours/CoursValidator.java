package org.miage.coursservice.entity.cours;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Service
@AllArgsConstructor
public class CoursValidator {

    private final Validator validator;

    public void validate(CoursInput cours) {
        Set<ConstraintViolation<CoursInput>> violations =
                validator.validate(cours);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}