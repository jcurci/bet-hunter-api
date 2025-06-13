package com.bethunter.bethunter_api.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestRegister(@NotBlank String login, @NotBlank String password,
                                            @NotBlank String name, @NotBlank String email, @NotBlank String cellphone) {
}
