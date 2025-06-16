package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.alternative.AlternativeRequestCreate;
import com.bethunter.bethunter_api.dto.alternative.AlternativeRequestUpdate;
import com.bethunter.bethunter_api.dto.alternative.AlternativeResponse;
import com.bethunter.bethunter_api.service.ServiceAlternative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("alternatives")
public class ControllerAlternative {

    @Autowired
    private ServiceAlternative serviceAlternative;

    @PostMapping
    public ResponseEntity<AlternativeResponse> createAlternative(@RequestBody AlternativeRequestCreate dto) {
        return serviceAlternative.createAlternative(dto);
    }

    @GetMapping
    public ResponseEntity<List<AlternativeResponse>> findAll() {
        return ResponseEntity.ok(serviceAlternative.findAll().stream()
                .map(alt -> {
                    return new AlternativeResponse(alt.getId(), alt.getQuestion().getId(), alt.getText(),
                            alt.isCorrect());
                }).collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    public ResponseEntity<AlternativeResponse> findById(@PathVariable String id) {
        return serviceAlternative.findById(id)
                .map(alt -> {
                    return ResponseEntity.status(200).body(new AlternativeResponse(alt.getId(),
                            alt.getQuestion().getId(), alt.getText(), alt.isCorrect()));
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<AlternativeResponse> updateAlternative(@PathVariable String id, @RequestBody AlternativeRequestUpdate dto) {
        return serviceAlternative.update(id, dto)
                .map(alt -> {
                    return ResponseEntity.status(200).body(new AlternativeResponse(alt.getId(),
                            alt.getQuestion().getId(), alt.getText(), alt.isCorrect()));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean result = serviceAlternative.delete(id);

        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
