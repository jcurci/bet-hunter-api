package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.question.QuestionQuizzResponse;
import com.bethunter.bethunter_api.dto.topic.TopicRequestCreate;
import com.bethunter.bethunter_api.model.Topic;
import com.bethunter.bethunter_api.service.ServiceQuestion;
import com.bethunter.bethunter_api.service.ServiceTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("quizz/{id}")
    public ResponseEntity<List<QuestionQuizzResponse>> findUnansweredQuestions(@RequestHeader(name = "Authorization") String token, @PathVariable String id) {
        return serviceQuestion.findUnansweredQuestions(token, id);
    }
}
