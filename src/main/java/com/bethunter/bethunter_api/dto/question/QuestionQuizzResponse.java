package com.bethunter.bethunter_api.dto.question;

import com.bethunter.bethunter_api.dto.alternative.AlternativeQuizzResponse;

import java.util.List;

public record QuestionQuizzResponse(String id, int question_number, String statement, List<AlternativeQuizzResponse> alternative) {
}
