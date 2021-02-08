package org.miage.oauthservice.boundary;

import lombok.AllArgsConstructor;
import org.miage.oauthservice.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

import static org.miage.oauthservice.entity.Role.ROLE_USER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/user", produces = APPLICATION_JSON_VALUE)
public class OAuthRepresentation {

    private final UserResource resource;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public Principal principal(Principal principal) {
        return principal;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid UserInput userInput) {
        Optional<User> user = resource.findById(userInput.getId());
        if (user.isEmpty()) {
            User u = new User();
            u.setId(userInput.getId());
            u.setUsername(userInput.getUsername());
            u.setPassword(passwordEncoder.encode(userInput.getPassword()));
            u.setEnabled(true);
            u.setCredentialsNonExpired(true);
            u.setAccountNonLocked(true);
            u.setAccountNonExpired(true);
            u.setRole(ROLE_USER);
            resource.save(u);
        }
        return ResponseEntity.ok().build();
    }
}