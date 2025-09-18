package com.bethunter.bethunter_api.mapper;

import com.bethunter.bethunter_api.dto.article.ArticleRequestCreate;
import com.bethunter.bethunter_api.dto.article.ArticleRequestUpdate;
import com.bethunter.bethunter_api.dto.article.ArticleResponseDTO;
import com.bethunter.bethunter_api.model.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    public Article toEntity(ArticleRequestCreate dto) {
        if (dto == null) return null;

        return new Article(dto.title());
    }

    public ArticleResponseDTO toResponseDTO(Article article) {
        if (article == null) return null;
        return new ArticleResponseDTO(
                article.getId(),
                article.getTitle()
        );
    }

    public void updateEntity(Article article, ArticleRequestUpdate dto) {
        if (article == null || dto == null) return;

        if (dto.title() != null) {
            article.setTitle(dto.title());
        }
    }
}