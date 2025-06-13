package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.UserRequestCreate;
import com.bethunter.bethunter_api.dto.UserResponse;
import com.bethunter.bethunter_api.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("users")
public class ControllerUser {

    @Autowired
    private ServiceUser serviceUser;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequestCreate dto) {
        return ResponseEntity.status(201).body("test");
    }

}
