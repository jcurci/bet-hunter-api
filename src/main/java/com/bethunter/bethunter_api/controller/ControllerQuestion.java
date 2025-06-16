package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.alternative.AlternativeRequestUpdate;
import com.bethunter.bethunter_api.dto.alternative.AlternativeResponse;
import com.bethunter.bethunter_api.dto.question.QuestionAnswerRequest;
import com.bethunter.bethunter_api.dto.question.QuestionRequestCreate;
import com.bethunter.bethunter_api.dto.question.QuestionRequestUpdate;
import com.bethunter.bethunter_api.dto.question.QuestionResponse;
import com.bethunter.bethunter_api.service.ServiceQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("questions")
public class ControllerQuestion {

    @Autowired
    private ServiceQuestion serviceQuestion;

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(@RequestBody QuestionRequestCreate dto) {
        return serviceQuestion.createQuestion(dto);
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> findAll() {
        return ResponseEntity.ok(serviceQuestion.findAll().stream()
                .map(question -> {
                    return new QuestionResponse(question.getId(),
                            question.getTopic().getId(), question.getQuestionNumber(), question.getStatement());
                }).collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    public ResponseEntity<QuestionResponse> findById(@PathVariable String id) {
        return serviceQuestion.findById(id)
                .map(question -> {
                    return ResponseEntity.status(200).body(new QuestionResponse(question.getId(),
                            question.getTopic().getId(), question.getQuestionNumber(), question.getStatement()));
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable String id, @RequestBody QuestionRequestUpdate dto) {
        return serviceQuestion.update(id, dto)
                .map(question -> {
                    return ResponseEntity.status(200).body(new QuestionResponse(question.getId(),
                            question.getTopic().getId(), question.getQuestionNumber(), question.getStatement()));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean result = serviceQuestion.delete(id);

        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("{id}/answer")
    public ResponseEntity<?> answerQuestion(@RequestHeader("Authorization") String token, @PathVariable String id, @RequestBody QuestionAnswerRequest answer) {
        return serviceQuestion.answerQuestion(token, id, answer);
    }


}
