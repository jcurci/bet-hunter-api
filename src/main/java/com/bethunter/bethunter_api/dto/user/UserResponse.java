package com.bethunter.bethunter_api.dto.user;

import java.math.BigDecimal;

public record UserResponse(String id, String email, String name, String cellphone, BigDecimal money, int points) {
}
