package com.bethunter.bethunter_api.service;

import com.bethunter.bethunter_api.dto.authentication.AuthenticationRequestLogin;
import com.bethunter.bethunter_api.dto.authentication.AuthenticationRequestPasswordChange;
import com.bethunter.bethunter_api.dto.authentication.AuthenticationRequestRegister;
import com.bethunter.bethunter_api.dto.authentication.LoginResponse;
import com.bethunter.bethunter_api.dto.user.UserResponse;
import com.bethunter.bethunter_api.exception.*;
import com.bethunter.bethunter_api.infra.security.ServiceToken;
import com.bethunter.bethunter_api.mapper.UserMapper;
import com.bethunter.bethunter_api.model.User;
import com.bethunter.bethunter_api.repository.RepositoryUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceAuthentication {

    // For some reason @Autowired is not working in this class
    private final RepositoryUser repositoryUser;
    private final ServiceToken serviceToken;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public LoginResponse login(AuthenticationRequestLogin dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = serviceToken.generateToken((User) auth.getPrincipal());

        return new LoginResponse(token);
    }

    public UserResponse register(AuthenticationRequestRegister dto) {
        if (repositoryUser.findByEmail(dto.email()).isPresent()) throw new EmailAlreadyRegistered();
        if (repositoryUser.findByCellphone(dto.cellphone()).isPresent()) throw new PhoneAlreadyRegistered();

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User user = new User(dto.email(), encryptedPassword, dto.name(),
                dto.cellphone());

        return userMapper.toResponseDTO(repositoryUser.save(user));
    }


    public void changePassword(String token, AuthenticationRequestPasswordChange dto) {
        var email = serviceToken.validateToken(token.replace("Bearer ", ""));
        if (email.isEmpty()) throw new InvalidToken();

        User user = repositoryUser.findByEmail(email).orElseThrow(() -> new UserNotFound());

        if (!passwordEncoder.matches(dto.current_password(), user.getPassword())) throw new PasswordsDoesntMatch();

        user.setPassword(passwordEncoder.encode(dto.new_password()));
        repositoryUser.save(user);

    }

}
