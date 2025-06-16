package com.bethunter.bethunter_api.dto.question;

import jakarta.validation.constraints.NotBlank;

public record QuestionRequestCreate(@NotBlank String id_topic, @NotBlank int question_number, @NotBlank String statement) {
}
