package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.lesson.LessonRequestCreate;
import com.bethunter.bethunter_api.dto.lesson.LessonRequestUpdate;
import com.bethunter.bethunter_api.dto.lesson.LessonUserProgressDTO;
import com.bethunter.bethunter_api.dto.lesson.LessonResponse;
import com.bethunter.bethunter_api.dto.topic.TopicProgressResponse;
import com.bethunter.bethunter_api.dto.topic.TopicResponse;
import com.bethunter.bethunter_api.service.ServiceLesson;
import com.bethunter.bethunter_api.service.ServiceTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lessons")
public class ControllerLesson {

    @Autowired
    private ServiceLesson serviceLesson;

    @Autowired
    private ServiceTopic serviceTopic;

    @PostMapping
    public ResponseEntity<LessonResponse> createLesson(@RequestBody LessonRequestCreate dto) {
        return ResponseEntity.status(201).body(serviceLesson.createLesson(dto));
    }

    @GetMapping
    public ResponseEntity<List<LessonResponse>> findAll() {
        return ResponseEntity.ok(serviceLesson.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<LessonResponse> findById(@PathVariable String id) {
        return serviceLesson.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<LessonResponse> updateLesson(@PathVariable String id,
                                                       @RequestBody LessonRequestUpdate dto) {
        return serviceLesson.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean result = serviceLesson.delete(id);
        return result ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("user_lessons")
    public ResponseEntity<List<LessonUserProgressDTO>> findUserLessons(
            @RequestHeader(name = "Authorization") String token) {
        return ResponseEntity.ok(serviceLesson.findUserLessons(token));
    }

    @GetMapping("{id}/topics")
    public ResponseEntity<List<TopicResponse>> findTopicsByLesson(@PathVariable String id) {
        return ResponseEntity.ok(serviceTopic.findAllByLessonId(id));
    }

    @GetMapping("user_topic_progress/{id}")
    public ResponseEntity<List<TopicProgressResponse>> findLessonTopicsWithUserProgress(
            @RequestHeader(name = "Authorization") String token,
            @PathVariable String id) {
        return ResponseEntity.ok(serviceTopic.findLessonTopicsWithUserProgress(token, id));
    }
}
