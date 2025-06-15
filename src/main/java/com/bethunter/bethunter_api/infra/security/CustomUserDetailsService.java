package com.bethunter.bethunter_api.infra.security;

import com.bethunter.bethunter_api.model.User;
import com.bethunter.bethunter_api.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private RepositoryUser repositoryUser;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repositoryUser.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
