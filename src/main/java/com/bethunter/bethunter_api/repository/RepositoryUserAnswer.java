package com.bethunter.bethunter_api.repository;

import com.bethunter.bethunter_api.model.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RepositoryUserAnswer extends JpaRepository<UserAnswer, String> {
    List<UserAnswer> findByUserIdAndQuestionTopicId(String userId, String idTopic);

    boolean existsByUserIdAndQuestionId(String idUser, String idQuestion);
}
