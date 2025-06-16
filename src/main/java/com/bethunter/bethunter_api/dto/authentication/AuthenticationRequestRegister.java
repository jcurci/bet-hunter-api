package com.bethunter.bethunter_api.dto.authentication;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestRegister(@NotBlank String email, @NotBlank String password,
                                            @NotBlank String name, @NotBlank String cellphone) {
}
