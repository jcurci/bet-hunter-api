package com.bethunter.bethunter_api.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRequestCreate(@NotBlank String login, @NotBlank String password, @NotBlank String name,
@NotBlank String cellphone, @NotBlank String email) {
}
