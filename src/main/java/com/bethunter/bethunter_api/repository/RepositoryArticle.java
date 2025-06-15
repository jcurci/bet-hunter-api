package com.bethunter.bethunter_api.repository;

import com.bethunter.bethunter_api.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryArticle extends JpaRepository<Article, String> {
}
