package com.bethunter.bethunter_api.repository;

import com.bethunter.bethunter_api.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryArticle extends JpaRepository<Article, String> {
}
