package com.bethunter.bethunter_api.repository;

import com.bethunter.bethunter_api.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryTopic extends JpaRepository<Topic, String> {
    int countTopicsByLessonId(String idLesson);
    List<Topic> findAllByLessonId(String idLesson);

}
