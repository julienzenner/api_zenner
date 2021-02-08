package org.miage.oauthservice.boundary;

import org.miage.oauthservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserResource extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}