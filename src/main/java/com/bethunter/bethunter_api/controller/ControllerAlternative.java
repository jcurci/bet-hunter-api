package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.alternative.AlternativeRequestCreate;
import com.bethunter.bethunter_api.dto.alternative.AlternativeResponse;
import com.bethunter.bethunter_api.service.ServiceAlternative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("alternatives")
public class ControllerAlternative {

    @Autowired
    private ServiceAlternative serviceAlternative;

    @PostMapping
    public ResponseEntity<AlternativeResponse> createAlternative(@RequestBody AlternativeRequestCreate dto) {
        return serviceAlternative.createAlternative(dto);
    }
}
