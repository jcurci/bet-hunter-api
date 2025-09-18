package com.bethunter.bethunter_api.controller;

import com.bethunter.bethunter_api.dto.article.ArticleRequestCreate;
import com.bethunter.bethunter_api.dto.article.ArticleRequestUpdate;
import com.bethunter.bethunter_api.dto.article.ArticleResponseDTO;
import com.bethunter.bethunter_api.service.ServiceArticle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("articles")
public class ControllerArticle {

    @Autowired
    private ServiceArticle serviceArticle;

    @Operation(summary = "Create a new article")
    @ApiResponse(responseCode = "201", description = "Created")
    @PostMapping
    public ResponseEntity<ArticleResponseDTO> createArticle(@RequestBody ArticleRequestCreate dto) {
        return ResponseEntity.status(201).body(serviceArticle.createArticle(dto));
    }

    @Operation(summary = "Find all articles in the database")
    @ApiResponse(responseCode = "200", description = "Found all")
    @GetMapping
    public ResponseEntity<List<ArticleResponseDTO>> findAll() {
        return ResponseEntity.ok(serviceArticle.findAll());
    }

    @Operation(summary = "Find a article by his id in the database")
    @ApiResponse(responseCode = "200", description = "Found")
    @GetMapping("{id}")
    public ResponseEntity<ArticleResponseDTO> findById(@PathVariable String id) {
        return serviceArticle.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update a article in the database")
    @ApiResponse(responseCode = "200", description = "Updated")
    @PutMapping("{id}")
    public ResponseEntity<ArticleResponseDTO> updateArticle(@PathVariable String id, @RequestBody ArticleRequestUpdate dto) {
        return serviceArticle.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a article in the database")
    @ApiResponse(responseCode = "204", description = "Deleted")
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
