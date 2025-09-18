package com.bethunter.bethunter_api.service;

import com.bethunter.bethunter_api.dto.authentication.AuthenticationRequestLogin;
import com.bethunter.bethunter_api.dto.authentication.AuthenticationRequestPasswordChange;
import com.bethunter.bethunter_api.dto.authentication.AuthenticationRequestRegister;
import com.bethunter.bethunter_api.dto.authentication.LoginResponse;
import com.bethunter.bethunter_api.exception.UserNotFound;
import com.bethunter.bethunter_api.infra.security.ServiceToken;
import com.bethunter.bethunter_api.model.User;
import com.bethunter.bethunter_api.repository.RepositoryUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServiceAuthentication {

    private final RepositoryUser repositoryUser;
    private final ServiceToken serviceToken;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public ServiceAuthentication(RepositoryUser repositoryUser, ServiceToken serviceToken, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.repositoryUser = repositoryUser;
        this.serviceToken = serviceToken;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(AuthenticationRequestLogin dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = serviceToken.generateToken((User) auth.getPrincipal());

        return new LoginResponse(token);
    }

    public ResponseEntity<?> register(AuthenticationRequestRegister dto) {
        if (repositoryUser.findByEmail(dto.email()).isPresent()) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User user = new User(dto.email(), encryptedPassword, dto.name(),
                dto.cellphone());

        repositoryUser.save(user);
        return ResponseEntity.ok().build();
    }


    public ResponseEntity<?> changePassword(String token, AuthenticationRequestPasswordChange dto) {
        var email = serviceToken.validateToken(token.replace("Bearer ", ""));
        if (email.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = repositoryUser.findByEmail(email).orElseThrow(() -> new UserNotFound());

        if (!passwordEncoder.matches(dto.current_password(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Wrong password");
        }

        user.setPassword(passwordEncoder.encode(dto.new_password()));
        repositoryUser.save(user);

        return ResponseEntity.ok().build();
    }

}
