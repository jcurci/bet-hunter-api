package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.UserResponse;
import com.bethunter.bethunter_api.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("users")
public class ControllerUser {

    @Autowired
    private ServiceUser serviceUser;

    @GetMapping
    public ResponseEntity<?> spinRoulete(@RequestHeader(name = "Authorization") String token) {
        return serviceUser.spinRoulete(token);
    }

}
