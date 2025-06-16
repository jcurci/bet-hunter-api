package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.user.UserRequestUpdate;
import com.bethunter.bethunter_api.dto.user.UserResponse;
import com.bethunter.bethunter_api.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("users")
public class ControllerUser {

    @Autowired
    private ServiceUser serviceUser;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(serviceUser.findAll().stream()
                .map(user -> {
                    return new UserResponse(user.getId(), user.getEmail(), user.getName(), user.getCellphone(),
                            user.getMoney(), user.getPoints());
                }).collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable String id) {
        return serviceUser.findById(id)
                .map(user -> {
                    return ResponseEntity.status(200).body(new UserResponse(user.getId(), user.getEmail(),
                            user.getName(), user.getCellphone(), user.getMoney(), user.getPoints()));
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String id, UserRequestUpdate dto) {
        return serviceUser.update(id, dto)
                .map(user -> {
                    return ResponseEntity.status(200).body(new UserResponse(user.getId(), user.getEmail(),
                            user.getName(), user.getCellphone(), user.getMoney(), user.getPoints()));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean result = serviceUser.deleteById(id);

        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("roulete")
    public ResponseEntity<?> spinRoulete(@RequestHeader(name = "Authorization") String token) {
        return serviceUser.spinRoulete(token);
    }

}
