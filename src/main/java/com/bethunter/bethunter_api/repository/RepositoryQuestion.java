package com.bethunter.bethunter_api.repository;

import com.bethunter.bethunter_api.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryQuestion extends JpaRepository<Question, String> {
    List<Question> findAllByTopicId(String topicId);
}
