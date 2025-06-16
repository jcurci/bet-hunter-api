package com.bethunter.bethunter_api.repository;

import com.bethunter.bethunter_api.model.UserProgressTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryUserProgressTopic extends JpaRepository<UserProgressTopic, String> {

    @Query("""
    SELECT COUNT(upt)
    FROM UserProgressTopic upt
    WHERE upt.user.id = :userId
      AND upt.topic.lesson.id = :lessonId
      AND upt.completed = true
    """)
    int countCompletedTopics(@Param("userId") String userId, @Param("lessonId") String lessonId);

    boolean existsByUserIdAndTopicIdAndCompletedTrue(String id, String id1);
}
