package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.lesson.LessonRequestCreate;
import com.bethunter.bethunter_api.model.Lesson;
import com.bethunter.bethunter_api.service.ServiceLesson;
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

//    @GetMapping("/user_lessons")
//    public ResponseEntity<List<Lesson>> findUserLessons() {
//
//    }
}
