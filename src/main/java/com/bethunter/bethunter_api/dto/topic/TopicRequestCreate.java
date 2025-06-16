package com.bethunter.bethunter_api.dto.topic;

import jakarta.validation.constraints.NotBlank;

public record TopicRequestCreate(@NotBlank String id_lesson) {
}
