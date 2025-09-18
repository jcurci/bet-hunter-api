package com.bethunter.bethunter_api.service;

import com.bethunter.bethunter_api.dto.article.ArticleRequestCreate;
import com.bethunter.bethunter_api.dto.article.ArticleRequestUpdate;
import com.bethunter.bethunter_api.dto.article.ArticleResponseDTO;
import com.bethunter.bethunter_api.mapper.ArticleMapper;
import com.bethunter.bethunter_api.model.Article;
import com.bethunter.bethunter_api.repository.RepositoryArticle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceArticle {

    @Autowired
    private RepositoryArticle repositoryArticle;

    @Autowired
    private ArticleMapper articleMapper;

    @Operation(summary = "Create a new article")
    @ApiResponse(responseCode = "201", description = "Created")
    @Transactional
    public ArticleResponseDTO createArticle(ArticleRequestCreate dto) {
        return articleMapper.toResponseDTO(repositoryArticle
                .save(articleMapper.toEntity(dto)));
    }

    @Operation(summary = "Find all articles in the database")
    @ApiResponse(responseCode = "200", description = "Found all")
    @Transactional(readOnly = true)
    public List<ArticleResponseDTO> findAll() {
        return repositoryArticle.findAll()
                .stream()
                .map(articleMapper::toResponseDTO)
                .toList();
    }

    @Operation(summary = "Find a article by his id in the database")
    @ApiResponse(responseCode = "200", description = "Found")
    @Transactional(readOnly = true)
    public Optional<ArticleResponseDTO> findById(String id) {
        return repositoryArticle.findById(id)
                .map(articleMapper::toResponseDTO);
    }

    @Operation(summary = "Update a article in the database")
    @ApiResponse(responseCode = "200", description = "Updated")
    @Transactional
    public Optional<ArticleResponseDTO> update(String id, ArticleRequestUpdate dto) {
        return repositoryArticle.findById(id)
                .map(article -> {
                    articleMapper.updateEntity(article, dto);
                    return articleMapper.toResponseDTO(repositoryArticle.save(article));
                });
    }

    @Operation(summary = "Delete a article in the database")
    @ApiResponse(responseCode = "204", description = "Deleted")
    @Transactional
    public boolean delete(String id) {
        if (repositoryArticle.existsById(id)) {
            repositoryArticle.deleteById(id);
            return true;
        }
        return false;
    }
}
