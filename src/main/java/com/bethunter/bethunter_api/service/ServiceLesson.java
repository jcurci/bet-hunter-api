package com.bethunter.bethunter_api.service;

import com.bethunter.bethunter_api.dto.lesson.LessonRequestCreate;
import com.bethunter.bethunter_api.model.Lesson;
import com.bethunter.bethunter_api.repository.RepositoryLesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceLesson {

    @Autowired
    private RepositoryLesson repositoryLesson;

    public Lesson createLesson(LessonRequestCreate dto) {
        return repositoryLesson.save(new Lesson(dto.title()));
    }

    public List<Lesson> findAll() {
        return repositoryLesson.findAll();
    }

    public Optional<Lesson> findById(String id) {
        return repositoryLesson.findById(id);
    }
}
