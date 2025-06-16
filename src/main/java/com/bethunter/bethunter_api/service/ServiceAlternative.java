package com.bethunter.bethunter_api.service;

import com.bethunter.bethunter_api.dto.alternative.AlternativeRequestCreate;
import com.bethunter.bethunter_api.dto.alternative.AlternativeRequestUpdate;
import com.bethunter.bethunter_api.dto.alternative.AlternativeResponse;
import com.bethunter.bethunter_api.model.Alternative;
import com.bethunter.bethunter_api.repository.RepositoryAlternative;
import com.bethunter.bethunter_api.repository.RepositoryQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceAlternative {

    @Autowired
    private RepositoryAlternative repositoryAlternative;

    @Autowired
    private RepositoryQuestion repositoryQuestion;

    public ResponseEntity<AlternativeResponse> createAlternative(AlternativeRequestCreate dto) {
        var question = repositoryQuestion.findById(dto.id_question());
        if (question.isPresent()) {
            Alternative alternative = repositoryAlternative.save(new Alternative(question.get(), dto.text(), dto.correct()));

            return ResponseEntity.status(201).body(new AlternativeResponse(alternative.getId(),
                    alternative.getQuestion().getId(), alternative.getText(), alternative.isCorrect()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<Alternative> findAll() {
        return repositoryAlternative.findAll();
    }

    public Optional<Alternative> findById(String id) {
        return repositoryAlternative.findById(id);
    }

    public Optional<Alternative> update(String id, AlternativeRequestUpdate dto) {
        var question = repositoryQuestion.findById(dto.id_question());
        if (question.isPresent()) {
            repositoryAlternative.findById(id)
                    .map(alt -> {
                        alt.setQuestion(question.get());
                        alt.setText(dto.text());
                        alt.setCorrect(dto.correct());
                        return repositoryAlternative.save(alt);
                    });
        }

        return null;
    }

    public boolean delete(String id) {
        if (repositoryAlternative.existsById(id)) {
            repositoryAlternative.deleteById(id);
            return true;
        }

        return false;
    }

}
