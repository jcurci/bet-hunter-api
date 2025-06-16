package com.bethunter.bethunter_api.dto.user;

import java.math.BigDecimal;

public record UserRequestUpdate(String email, String name, String cellphone, BigDecimal money, int points) {
}
