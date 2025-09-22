package com.bethunter.bethunter_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerTest {

    @GetMapping("/")
    public String home() {
        return "Aplicação de teste para ZAP";
    }

}
