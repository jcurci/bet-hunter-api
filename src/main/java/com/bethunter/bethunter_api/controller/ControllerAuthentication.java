package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.AuthenticationRequestLogin;
import com.bethunter.bethunter_api.dto.AuthenticationRequestRegister;
import com.bethunter.bethunter_api.dto.LoginResponse;
import com.bethunter.bethunter_api.infra.security.ServiceToken;
import com.bethunter.bethunter_api.model.User;
import com.bethunter.bethunter_api.repository.RepositoryUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class ControllerAuthentication {

    private final RepositoryUser repositoryUser;
    private final ServiceToken serviceToken;
    private final AuthenticationManager authenticationManager;

    public ControllerAuthentication(
            RepositoryUser repositoryUser,
            ServiceToken serviceToken,
            AuthenticationManager authenticationManager) {
        this.repositoryUser = repositoryUser;
        this.serviceToken = serviceToken;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequestLogin dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = serviceToken.generateToken((User) auth.getPrincipal());

        return ResponseEntity.status(200).body(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid AuthenticationRequestRegister dto) {
        if (repositoryUser.findByEmail(dto.email()).isPresent()) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User user = new User(dto.email(), encryptedPassword, dto.name(),
                dto.cellphone());

        repositoryUser.save(user);
        return ResponseEntity.ok().build();
    }
}
