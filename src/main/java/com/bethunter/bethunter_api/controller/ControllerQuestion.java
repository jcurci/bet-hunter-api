package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.question.QuestionAnswerRequest;
import com.bethunter.bethunter_api.dto.question.QuestionRequestCreate;
import com.bethunter.bethunter_api.dto.question.QuestionResponse;
import com.bethunter.bethunter_api.service.ServiceQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("questions")
public class ControllerQuestion {

    @Autowired
    private ServiceQuestion serviceQuestion;

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(@RequestBody QuestionRequestCreate dto) {
        return serviceQuestion.createQuestion(dto);
    }

    @PostMapping("{id}/answer")
    public ResponseEntity<?> answerQuestion(@RequestHeader("Authorization") String token, @PathVariable String id, @RequestBody QuestionAnswerRequest answer) {
        return serviceQuestion.answerQuestion(token, id, answer);
    }


}
