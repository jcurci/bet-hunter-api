package com.bethunter.bethunter_api.service;

import com.bethunter.bethunter_api.dto.question.QuestionRequestUpdate;
import com.bethunter.bethunter_api.dto.topic.TopicProgressResponse;
import com.bethunter.bethunter_api.dto.topic.TopicRequestCreate;
import com.bethunter.bethunter_api.dto.topic.TopicRequestUpdate;
import com.bethunter.bethunter_api.infra.security.ServiceToken;
import com.bethunter.bethunter_api.model.Question;
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

    public ResponseEntity<Topic> createTopic(TopicRequestCreate dto) {
        var lesson = repositoryLesson.findById(dto.id_lesson());
        if (lesson.isPresent()) {
            return ResponseEntity.status(201).body(repositoryTopic.save(new Topic(lesson.get())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<Topic> findAll() {
        return repositoryTopic.findAll();
    }

    public Optional<Topic> findById(String id) {
        return repositoryTopic.findById(id);
    }

    public Optional<Topic> update(String id, TopicRequestUpdate dto) {
        var lesson = repositoryLesson.findById(dto.id_lesson());
        if (lesson.isPresent()) {
            repositoryTopic.findById(id)
                    .map(topic -> {
                        topic.setLesson(lesson.get());
                        return repositoryTopic.save(topic);
                    });
        }

        return null;
    }

    public boolean delete(String id) {
        if (repositoryLesson.existsById(id)) {
            repositoryLesson.deleteById(id);
            return true;
        }

        return false;
    }

    public List<Topic> findAllByLessonId(String id) {
        return repositoryTopic.findAllByLessonId(id);
    }

    public ResponseEntity<List<TopicProgressResponse>> findLessonTopicsWithUserProgress(String token, String idLesson) {
        var email = serviceToken.validateToken(token.replace("Bearer ", ""));
        if (email.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = repositoryUser.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Topic> topics = repositoryTopic.findAllByLessonId(idLesson);

        List<TopicProgressResponse> result = topics.stream().map(topic -> {
            boolean completed = repositoryUserProgressTopic
                    .existsByUserIdAndTopicIdAndCompletedTrue(user.getId(), topic.getId());

            return new TopicProgressResponse(topic.getId(), topic.getLesson().getId(), completed);
        }).toList();

        return ResponseEntity.ok(result);
    }
}
