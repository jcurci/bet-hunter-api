package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.AiRequestText;
import com.bethunter.bethunter_api.infra.openai.CallApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("AI")
public class ControllerAI {

    @Autowired
    private CallApi callApi;

    @GetMapping()
    public ResponseEntity<?> askAI(@RequestBody AiRequestText aiRequestText) throws IOException, InterruptedException {
        return ResponseEntity.ok(callApi.getAnalysisOfText(aiRequestText.text()));
    }
}
