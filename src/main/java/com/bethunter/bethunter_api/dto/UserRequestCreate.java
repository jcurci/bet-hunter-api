package com.bethunter.bethunter_api.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRequestCreate(@NotBlank String email, @NotBlank String password, @NotBlank String name,
@NotBlank String cellphone) {
}
