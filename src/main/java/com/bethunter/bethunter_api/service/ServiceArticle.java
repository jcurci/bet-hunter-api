package com.bethunter.bethunter_api.service;

import com.bethunter.bethunter_api.dto.article.ArticleRequestCreate;
import com.bethunter.bethunter_api.dto.article.ArticleRequestUpdate;
import com.bethunter.bethunter_api.model.Article;
import com.bethunter.bethunter_api.repository.RepositoryArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Article> findById(String id) {
        return repositoryArticle.findById(id);
    }

    public Optional<Article> update(String id, ArticleRequestUpdate dto) {
        repositoryArticle.findById(id)
                .map(article -> {
                    article.setTitle(dto.title());
                    return repositoryArticle.save(article);
                });

        return null;
    }

    public boolean delete(String id) {
        if (repositoryArticle.existsById(id)) {
            repositoryArticle.deleteById(id);
            return true;
        }

        return false;
    }
}
