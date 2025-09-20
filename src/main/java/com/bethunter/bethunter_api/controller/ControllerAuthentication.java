package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.authentication.AuthenticationRequestLogin;
import com.bethunter.bethunter_api.dto.authentication.AuthenticationRequestPasswordChange;
import com.bethunter.bethunter_api.dto.authentication.AuthenticationRequestRegister;
import com.bethunter.bethunter_api.dto.authentication.LoginResponse;
import com.bethunter.bethunter_api.dto.user.UserResponse;
import com.bethunter.bethunter_api.service.ServiceAuthentication;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class ControllerAuthentication {

    @Autowired
    private ServiceAuthentication serviceAuthentication;

    // @CrossOrigin(origins = "*")
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid AuthenticationRequestRegister dto) {
        return ResponseEntity.status(201).body(serviceAuthentication.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthenticationRequestLogin dto) {
        return ResponseEntity.status(200).body(serviceAuthentication.login(dto));
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestHeader(name = "Authorization") String token, @RequestBody AuthenticationRequestPasswordChange dto) {
        serviceAuthentication.changePassword(token, dto);
        return ResponseEntity.ok().build();
    }

}
