package org.miage.oauthservice.boundary;

import lombok.AllArgsConstructor;
import org.miage.oauthservice.entity.User;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserResource resource;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = this.resource.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur " + username + " introuvable"));
        new AccountStatusUserDetailsChecker().check(user);
        return user;
    }
}