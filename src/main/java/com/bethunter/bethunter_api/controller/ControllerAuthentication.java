package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.authentication.AuthenticationRequestLogin;
import com.bethunter.bethunter_api.dto.authentication.AuthenticationRequestPasswordChange;
import com.bethunter.bethunter_api.dto.authentication.AuthenticationRequestRegister;
import com.bethunter.bethunter_api.dto.authentication.LoginResponse;
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



    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthenticationRequestLogin dto) {
        return ResponseEntity.status(200).body(serviceAuthentication.login(dto));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid AuthenticationRequestRegister dto) {
        return serviceAuthentication.register(dto);
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@RequestHeader(name = "Authorization") String token, @RequestBody AuthenticationRequestPasswordChange dto) {
        return serviceAuthentication.changePassword(token, dto);
    }


}
