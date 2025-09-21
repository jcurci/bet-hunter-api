package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.question.*;
import com.bethunter.bethunter_api.dto.useranswer.UserAnswerResponse;
import com.bethunter.bethunter_api.exception.QuestionNotFound;
import com.bethunter.bethunter_api.service.ServiceQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("questions")
public class ControllerQuestion {

    @Autowired
    private ServiceQuestion serviceQuestion;

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(@RequestBody QuestionRequestCreate dto) {
        return ResponseEntity.status(201).body(serviceQuestion.createQuestion(dto));
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> findAll() {
        return ResponseEntity.ok(serviceQuestion.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<QuestionResponse> findById(@PathVariable String id) {
        return serviceQuestion.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new QuestionNotFound());
    }

    @PutMapping("{id}")
    public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable String id, @RequestBody QuestionRequestUpdate dto) {
        return serviceQuestion.update(id, dto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new QuestionNotFound());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean result = serviceQuestion.delete(id);

        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            throw new QuestionNotFound();
        }
    }

    @GetMapping("unanswered/{idTopic}")
    public ResponseEntity<List<QuestionQuizzResponse>> findUnansweredQuestionsFromTopic(@RequestHeader("Authorization") String token,
                                                                               @PathVariable String idTopic) {
        List<QuestionQuizzResponse> questions = serviceQuestion.findUnansweredQuestions(token, idTopic);
        return ResponseEntity.ok(questions);
    }

    @PostMapping("{id}/answer")
    public ResponseEntity<UserAnswerResponse> answerQuestion(@RequestHeader("Authorization") String token, @PathVariable String id, @RequestBody QuestionAnswerRequest answer) {
        return ResponseEntity.ok(serviceQuestion.answerQuestion(token, id, answer));
    }

}
