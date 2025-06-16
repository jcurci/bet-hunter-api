package com.bethunter.bethunter_api.dto.article;

import jakarta.validation.constraints.NotBlank;

public record ArticleRequestCreate(@NotBlank String title) {
}
