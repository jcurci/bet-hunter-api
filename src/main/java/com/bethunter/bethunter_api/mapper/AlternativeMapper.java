package com.bethunter.bethunter_api.mapper;

import com.bethunter.bethunter_api.dto.alternative.*;
import com.bethunter.bethunter_api.model.Alternative;
import com.bethunter.bethunter_api.model.Question;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlternativeMapper {

    public Alternative toEntity(AlternativeRequestCreate dto, Question question) {
        if (dto == null || question == null) return null;
        return new Alternative(
                question,
                dto.text(),
                dto.correct()
        );
    }

    public Alternative toEntity(AlternativeRequestUpdate dto, Question question, Alternative existing) {
        if (dto == null || question == null || existing == null) return null;

        existing.setQuestion(question);
        existing.setText(dto.text());
        existing.setCorrect(dto.correct());
        return existing;
    }

    public AlternativeResponse toResponseDTO(Alternative alternative) {
        if (alternative == null) return null;

        return new AlternativeResponse(
                alternative.getId(),
                alternative.getQuestion().getId(),
                alternative.getText(),
                alternative.isCorrect()
        );
    }

    public Alternative toEntityUpdate(Alternative alt, AlternativeRequestUpdate dto, Question question) {
        alt.setQuestion(question);
        alt.setText(dto.text());
        alt.setCorrect(dto.correct());
        return alt;
    }

    public AlternativeQuizzResponse toQuizzResponseDTO(Alternative alternative) {
        if (alternative == null) return null;

        return new AlternativeQuizzResponse(
                alternative.getId(),
                alternative.getText(),
                alternative.isCorrect()
        );
    }

    public List<AlternativeQuizzResponse> toQuizzResponseList(List<Alternative> alternatives) {
        if (alternatives == null) return List.of();
        return alternatives.stream()
                .map(this::toQuizzResponseDTO)
                .collect(Collectors.toList());
    }
}