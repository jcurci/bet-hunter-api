package com.bethunter.bethunter_api.dto.lesson;

import jakarta.validation.constraints.NotBlank;

public record LessonRequestCreate(@NotBlank String title) {
}
