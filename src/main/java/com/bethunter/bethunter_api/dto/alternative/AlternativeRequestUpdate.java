package com.bethunter.bethunter_api.dto.alternative;

import jakarta.validation.constraints.NotBlank;

public record AlternativeRequestUpdate(@NotBlank String id_question, @NotBlank String text, boolean correct) {
}
