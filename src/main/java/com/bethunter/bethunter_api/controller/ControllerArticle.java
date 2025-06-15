package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.ArticleRequestCreate;
import com.bethunter.bethunter_api.model.Article;
import com.bethunter.bethunter_api.service.ServiceArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("articles")
public class ControllerArticle {

    @Autowired
    private ServiceArticle serviceArticle;

    @PostMapping
    public ResponseEntity<?> createArticle(@RequestBody ArticleRequestCreate dto) {
        return ResponseEntity.status(201).body(serviceArticle.createArticle(dto));
    }

    @GetMapping
    public ResponseEntity<List<Article>> findAll() {
        return ResponseEntity.ok(serviceArticle
                .findAll()
                .stream()
                .map(article -> new Article(article.getTitle()))
                .collect(Collectors.toList()));
    }
}
