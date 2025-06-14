package com.bethunter.bethunter_api.service;

import com.bethunter.bethunter_api.dto.ArticleRequestCreate;
import com.bethunter.bethunter_api.model.Article;
import com.bethunter.bethunter_api.repository.RepositoryArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceArticle {

    @Autowired
    private RepositoryArticle repositoryArticle;

    public Article createArticle(ArticleRequestCreate dto) {
        return repositoryArticle.save(new Article(dto.title()));
    }

    public List<Article> findAll() {
        return repositoryArticle.findAll();
    }
}
