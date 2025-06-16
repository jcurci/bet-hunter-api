package com.bethunter.bethunter_api.dto.topic;

import jakarta.validation.constraints.NotBlank;

public record TopicRequestUpdate(@NotBlank String id_lesson) {
}
