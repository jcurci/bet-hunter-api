package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.question.QuestionQuizzResponse;
import com.bethunter.bethunter_api.dto.question.QuestionResponse;
import com.bethunter.bethunter_api.dto.topic.TopicRequestCreate;
import com.bethunter.bethunter_api.dto.topic.TopicRequestUpdate;
import com.bethunter.bethunter_api.dto.topic.TopicResponse;
import com.bethunter.bethunter_api.model.Topic;
import com.bethunter.bethunter_api.service.ServiceQuestion;
import com.bethunter.bethunter_api.service.ServiceTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("topics")
public class ControllerTopic {

    @Autowired
    private ServiceTopic serviceTopic;

    @Autowired
    private ServiceQuestion serviceQuestion;

    @PostMapping
    public ResponseEntity<Topic> createTopic(@RequestBody TopicRequestCreate dto) {
        return serviceTopic.createTopic(dto);
    }

    @GetMapping
    public ResponseEntity<List<TopicResponse>> findAll() {
        return ResponseEntity.ok(serviceTopic.findAll().stream()
                .map(topic -> {
                    return new TopicResponse(topic.getId(), topic.getLesson().getId());
                }).collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    public ResponseEntity<TopicResponse> findById(@PathVariable String id) {
        return serviceTopic.findById(id)
                .map(topic -> {
                    return ResponseEntity.status(200).body(new TopicResponse(topic.getId(), topic.getLesson().getId()));
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<TopicResponse> updateTopic(@PathVariable String id, @RequestBody TopicRequestUpdate dto) {
        return serviceTopic.update(id, dto)
                .map(topic -> {
                    return ResponseEntity.status(200).body(new TopicResponse(topic.getId(), topic.getLesson().getId()));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean result = serviceTopic.delete(id);

        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("quizz/{id}")
    public ResponseEntity<List<QuestionQuizzResponse>> findUnansweredQuestions(@RequestHeader(name = "Authorization") String token, @PathVariable String id) {
        return serviceQuestion.findUnansweredQuestions(token, id);
    }
}
