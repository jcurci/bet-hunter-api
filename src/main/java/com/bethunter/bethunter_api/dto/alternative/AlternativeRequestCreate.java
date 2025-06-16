package com.bethunter.bethunter_api.dto.alternative;

import jakarta.validation.constraints.NotBlank;

public record AlternativeRequestCreate(@NotBlank String id_question, @NotBlank String text, boolean correct) {
}
