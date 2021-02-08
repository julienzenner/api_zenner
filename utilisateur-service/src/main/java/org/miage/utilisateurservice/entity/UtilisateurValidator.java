package org.miage.utilisateurservice.entity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Service
@AllArgsConstructor
public class UtilisateurValidator {

    private final Validator validator;

    public void validate(UtilisateurInput utilisateur) {
        Set<ConstraintViolation<UtilisateurInput>> violations =
                validator.validate(utilisateur);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}