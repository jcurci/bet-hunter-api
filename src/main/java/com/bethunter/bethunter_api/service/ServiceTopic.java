package com.bethunter.bethunter_api.service;

import com.bethunter.bethunter_api.dto.topic.TopicProgressResponse;
import com.bethunter.bethunter_api.dto.topic.TopicRequestCreate;
import com.bethunter.bethunter_api.dto.topic.TopicRequestUpdate;
import com.bethunter.bethunter_api.dto.topic.TopicResponse;
import com.bethunter.bethunter_api.exception.InvalidToken;
import com.bethunter.bethunter_api.exception.UserNotFound;
import com.bethunter.bethunter_api.infra.security.ServiceToken;
import com.bethunter.bethunter_api.mapper.TopicMapper;
import com.bethunter.bethunter_api.model.Lesson;
import com.bethunter.bethunter_api.model.Topic;
import com.bethunter.bethunter_api.model.User;
import com.bethunter.bethunter_api.repository.RepositoryLesson;
import com.bethunter.bethunter_api.repository.RepositoryTopic;
import com.bethunter.bethunter_api.repository.RepositoryUser;
import com.bethunter.bethunter_api.repository.RepositoryUserProgressTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceTopic {

    @Autowired
    private RepositoryTopic repositoryTopic;

    @Autowired
    private RepositoryLesson repositoryLesson;

    @Autowired
    private ServiceToken serviceToken;

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private RepositoryUserProgressTopic repositoryUserProgressTopic;

    @Autowired
    private TopicMapper topicMapper;

    public ResponseEntity<TopicResponse> createTopic(TopicRequestCreate dto) {
        Optional<Lesson> lesson = repositoryLesson.findById(dto.id_lesson());

        if (lesson.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Topic topic = topicMapper.toEntity(dto, lesson.get());
        Topic saved = repositoryTopic.save(topic);

        return ResponseEntity.status(201).body(topicMapper.toResponseDTO(saved));
    }

    public List<TopicResponse> findAll() {
        return repositoryTopic.findAll().stream()
                .map(topicMapper::toResponseDTO)
                .toList();
    }

    public Optional<TopicResponse> findById(String id) {
        return repositoryTopic.findById(id)
                .map(topicMapper::toResponseDTO);
    }

    public Optional<TopicResponse> update(String id, TopicRequestUpdate dto) {
        return repositoryLesson.findById(dto.id_lesson())
                .flatMap(lesson ->
                        repositoryTopic.findById(id)
                                .map(topic -> {
                                    topicMapper.updateEntity(topic, dto, lesson);
                                    Topic updated = repositoryTopic.save(topic);
                                    return topicMapper.toResponseDTO(updated);
                                })
                );
    }

    public boolean delete(String id) {
        if (repositoryTopic.existsById(id)) {
            repositoryTopic.deleteById(id);
            return true;
        }
        return false;
    }

    public List<TopicResponse> findAllByLessonId(String id) {
        return repositoryTopic.findAllByLessonId(id).stream()
                .map(topicMapper::toResponseDTO)
                .toList();
    }

    public List<TopicProgressResponse> findLessonTopicsWithUserProgress(String token, String idLesson) {
        var email = serviceToken.validateToken(token.replace("Bearer ", ""));
        if (email.isEmpty()) {
            throw new InvalidToken();
        }

        User user = repositoryUser.findByEmail(email)
                .orElseThrow(() -> new UserNotFound());

        List<Topic> topics = repositoryTopic.findAllByLessonId(idLesson);

        return topics.stream().map(topic -> {
            boolean completed = repositoryUserProgressTopic
                    .existsByUserIdAndTopicIdAndCompletedTrue(user.getId(), topic.getId());

            return new TopicProgressResponse(topic.getId(), topic.getLesson().getId(), completed);
        }).toList();
    }
}