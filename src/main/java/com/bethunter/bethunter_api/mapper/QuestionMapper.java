package com.bethunter.bethunter_api.mapper;

import com.bethunter.bethunter_api.dto.alternative.AlternativeQuizzResponse;
import com.bethunter.bethunter_api.dto.question.*;
import com.bethunter.bethunter_api.model.Question;
import com.bethunter.bethunter_api.model.Topic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {

    public Question toEntity(QuestionRequestCreate dto, Topic topic) {
        if (dto == null || topic == null) return null;

        return new Question(
                topic,
                dto.question_number(),
                dto.statement()
        );
    }

    public Question toEntity(QuestionRequestUpdate dto, Topic topic, Question existing) {
        if (dto == null || topic == null || existing == null) return null;

        existing.setTopic(topic);
        existing.setQuestionNumber(dto.question_number());
        existing.setStatement(dto.statement());
        return existing;
    }

    public QuestionResponse toResponseDTO(Question question) {
        if (question == null) return null;

        return new QuestionResponse(
                question.getId(),
                question.getTopic().getId(),
                question.getQuestionNumber(),
                question.getStatement()
        );
    }

    public QuestionQuizzResponse toQuizzResponseDTO(Question question) {
        if (question == null) return null;

        List<AlternativeQuizzResponse> alternatives = question.getAlternatives() == null
                ? List.of()
                : question.getAlternatives().stream()
                .map(a -> new AlternativeQuizzResponse(a.getId(), a.getText(), a.isCorrect()))
                .collect(Collectors.toList());

        return new QuestionQuizzResponse(
                question.getId(),
                question.getQuestionNumber(),
                question.getStatement(),
                alternatives
        );
    }
}
