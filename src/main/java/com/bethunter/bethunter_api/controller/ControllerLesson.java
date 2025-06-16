package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.lesson.LessonRequestCreate;
import com.bethunter.bethunter_api.dto.lesson.LessonUserProgressDTO;
import com.bethunter.bethunter_api.dto.topic.TopicProgressResponse;
import com.bethunter.bethunter_api.dto.topic.TopicResponse;
import com.bethunter.bethunter_api.model.Lesson;
import com.bethunter.bethunter_api.model.Topic;
import com.bethunter.bethunter_api.service.ServiceLesson;
import com.bethunter.bethunter_api.service.ServiceTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("lessons")
public class ControllerLesson {

    @Autowired
    private ServiceLesson serviceLesson;

    @Autowired
    private ServiceTopic serviceTopic;

    @PostMapping
    public ResponseEntity<?> createArticle(@RequestBody LessonRequestCreate dto) {
        return ResponseEntity.status(201).body(serviceLesson.createLesson(dto));
    }

    @GetMapping
    public ResponseEntity<List<Lesson>> findAll() {
        return ResponseEntity.ok(serviceLesson.findAll().stream()
                .map(lesson -> {
                    return new Lesson(lesson.getId(), lesson.getTitle());
                }).collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    public ResponseEntity<Lesson> findById(@PathVariable String id) {
        return serviceLesson.findById(id)
                .map(lesson -> {
                    return ResponseEntity.status(200).body(new Lesson(lesson.getId(), lesson.getTitle()));
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("user_lessons")
    public ResponseEntity<List<LessonUserProgressDTO>> findUserLessons(@RequestHeader(name = "Authorization") String token) {
        return serviceLesson.findUserLessons(token);
    }

    @GetMapping("/{id}/topics")
    public ResponseEntity<List<TopicResponse>> findTopicsByLesson(@PathVariable String id) {
        return ResponseEntity.ok(serviceTopic.findAllByLessonId(id).stream()
                .map(topic -> {
                    return new TopicResponse(topic.getId(), topic.getLesson().getId());
                }).collect(Collectors.toList()));
    }

    @GetMapping("user_topic_progress/{id}")
    public ResponseEntity<List<TopicProgressResponse>> findLessonTopicsWithUserProgress(@RequestHeader(name = "Authorization") String token, @PathVariable String id) {
        return serviceTopic.findLessonTopicsWithUserProgress(token, id);
    }

}
