package com.bethunter.bethunter_api.service;

import com.bethunter.bethunter_api.dto.lesson.LessonRequestCreate;
import com.bethunter.bethunter_api.dto.lesson.LessonRequestUpdate;
import com.bethunter.bethunter_api.dto.lesson.LessonUserProgressDTO;
import com.bethunter.bethunter_api.dto.lesson.LessonResponse;
import com.bethunter.bethunter_api.mapper.LessonMapper;
import com.bethunter.bethunter_api.model.Lesson;
import com.bethunter.bethunter_api.model.User;
import com.bethunter.bethunter_api.repository.*;
import com.bethunter.bethunter_api.infra.security.ServiceToken;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private LessonMapper lessonMapper;

    public LessonResponse createLesson(LessonRequestCreate dto) {
        Lesson lesson = lessonMapper.toEntity(dto);
        Lesson saved = repositoryLesson.save(lesson);
        return lessonMapper.toUserResponse(saved);
    }

    public List<LessonResponse> findAll() {
        return repositoryLesson.findAll()
                .stream()
                .map(lessonMapper::toUserResponse)
                .toList();
    }

    public Optional<LessonResponse> findById(String id) {
        return repositoryLesson.findById(id)
                .map(lessonMapper::toUserResponse);
    }

    public List<LessonUserProgressDTO> findUserLessons(String token) {
        var email = serviceToken.validateToken(token.replace("Bearer ", ""));
        if (email.isEmpty()) {
            throw new RuntimeException("Invalid token");
        }

        User user = repositoryUser.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Lesson> lessons = repositoryLesson.findAll();

        return lessons.stream().map(lesson -> {
            int totalTopics = repositoryTopic.countTopicsByLessonId(lesson.getId());
            int completedTopics = repositoryUserProgressTopic.countCompletedTopics(user.getId(), lesson.getId());
            return lessonMapper.toUserProgressDTO(lesson, totalTopics, completedTopics);
        }).toList();
    }

    public Optional<LessonResponse> update(String id, LessonRequestUpdate dto) {
        return repositoryLesson.findById(id)
                .map(lesson -> {
                    lessonMapper.updateEntity(lesson, dto);
                    Lesson updated = repositoryLesson.save(lesson);
                    return lessonMapper.toUserResponse(updated);
                });
    }

    public boolean delete(String id) {
        if (repositoryLesson.existsById(id)) {
            repositoryLesson.deleteById(id);
            return true;
        }
        return false;
    }
}
