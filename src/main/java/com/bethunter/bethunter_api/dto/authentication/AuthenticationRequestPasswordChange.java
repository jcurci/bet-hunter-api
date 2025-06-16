package com.bethunter.bethunter_api.dto.authentication;

public record AuthenticationRequestPasswordChange(String current_password, String new_password) {
}
