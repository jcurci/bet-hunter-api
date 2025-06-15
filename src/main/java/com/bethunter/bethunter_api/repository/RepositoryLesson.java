package com.bethunter.bethunter_api.repository;

import com.bethunter.bethunter_api.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryLesson extends JpaRepository<Lesson, String> {
}
