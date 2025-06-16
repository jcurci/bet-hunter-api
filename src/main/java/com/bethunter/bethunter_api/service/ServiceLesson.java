package com.bethunter.bethunter_api.service;

import com.bethunter.bethunter_api.dto.alternative.AlternativeRequestUpdate;
import com.bethunter.bethunter_api.dto.lesson.LessonRequestCreate;
import com.bethunter.bethunter_api.dto.lesson.LessonRequestUpdate;
import com.bethunter.bethunter_api.dto.lesson.LessonUserProgressDTO;
import com.bethunter.bethunter_api.infra.security.ServiceToken;
import com.bethunter.bethunter_api.model.*;
import com.bethunter.bethunter_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceLesson {

    @Autowired
    private RepositoryLesson repositoryLesson;

    @Autowired
    private ServiceToken serviceToken;

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private RepositoryTopic repositoryTopic;

    @Autowired
    private RepositoryUserProgressTopic repositoryUserProgressTopic;

    public Lesson createLesson(LessonRequestCreate dto) {
        return repositoryLesson.save(new Lesson(dto.title()));
    }

    public List<Lesson> findAll() {
        return repositoryLesson.findAll();
    }

    public Optional<Lesson> findById(String id) {
        return repositoryLesson.findById(id);
    }

    public ResponseEntity<List<LessonUserProgressDTO>> findUserLessons(String token) {
        var email = serviceToken.validateToken(token.replace("Bearer ", ""));
        if (email.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = repositoryUser.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Lesson> lessons = repositoryLesson.findAll();

        List<LessonUserProgressDTO> result = lessons.stream().map(lesson -> {
            int totalTopics = repositoryTopic.countTopicsByLessonId(lesson.getId());
            int completedTopics = repositoryUserProgressTopic.countCompletedTopics(user.getId(), lesson.getId());

            double progress = totalTopics == 0 ? 0 : (completedTopics * 100.0 / totalTopics);

            return new LessonUserProgressDTO(
                    lesson.getId(),
                    lesson.getTitle(),
                    totalTopics,
                    completedTopics,
                    Math.round(progress * 100.0) / 100.0
            );
        }).toList();

        return ResponseEntity.ok(result);
    }

    public Optional<Lesson> update(String id, LessonRequestUpdate dto) {
        repositoryLesson.findById(id)
                .map(lesson -> {
                    lesson.setTitle(dto.title());
                    return repositoryLesson.save(lesson);
                });

        return null;
    }

    public boolean delete(String id) {
        if (repositoryLesson.existsById(id)) {
            repositoryLesson.deleteById(id);
            return true;
        }

        return false;
    }
}
