package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.user.UserRequestUpdate;
import com.bethunter.bethunter_api.dto.user.UserResponse;
import com.bethunter.bethunter_api.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("users")
public class ControllerUser {

    @Autowired
    private ServiceUser serviceUser;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(serviceUser.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable String id) {
        return serviceUser.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String id, UserRequestUpdate dto) {
        return serviceUser.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean result = serviceUser.delete(id);

        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("roulete")
    public ResponseEntity<?> spinRoulete(@RequestHeader(name = "Authorization") String token) {
        return ResponseEntity.ok(serviceUser.spinRoulete(token));
    }
}
