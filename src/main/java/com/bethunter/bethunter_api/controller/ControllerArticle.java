package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.article.ArticleRequestCreate;
import com.bethunter.bethunter_api.dto.article.ArticleRequestUpdate;
import com.bethunter.bethunter_api.dto.article.ArticleResponseDTO;
import com.bethunter.bethunter_api.service.ServiceArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("articles")
public class ControllerArticle {

    @Autowired
    private ServiceArticle serviceArticle;

    @PostMapping
    public ResponseEntity<ArticleResponseDTO> createArticle(@RequestBody ArticleRequestCreate dto) {
        return ResponseEntity.status(201).body(serviceArticle.createArticle(dto));
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponseDTO>> findAll() {
        return ResponseEntity.ok(serviceArticle.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<ArticleResponseDTO> findById(@PathVariable String id) {
        return serviceArticle.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<ArticleResponseDTO> updateArticle(@PathVariable String id, @RequestBody ArticleRequestUpdate dto) {
        return serviceArticle.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean result = serviceArticle.delete(id);

        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
