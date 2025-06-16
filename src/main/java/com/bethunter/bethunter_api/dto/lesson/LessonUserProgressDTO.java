package com.bethunter.bethunter_api.dto.lesson;

import jakarta.validation.constraints.NotBlank;

public record LessonUserProgressDTO(@NotBlank String id, @NotBlank String title, @NotBlank int totalTopics,
                                    @NotBlank int completedTopics, double progressPercent) {
}
