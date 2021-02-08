package org.miage.oauthservice.boundary;

import lombok.AllArgsConstructor;
import org.miage.oauthservice.entity.User;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.miage.oauthservice.entity.Role.ROLE_USER;

@Component
@AllArgsConstructor
public class UserMessageProcessor {

    private final UserResource resource;
    private final PasswordEncoder passwordEncoder;

    @StreamListener("new-user")
    @Transactional
    public void onMessageNewUser(Message<UserInput> msg) {
        UserInput userInput = msg.getPayload();
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
    }
}