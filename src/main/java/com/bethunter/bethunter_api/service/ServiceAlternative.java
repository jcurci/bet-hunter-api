package com.bethunter.bethunter_api.service;

import com.bethunter.bethunter_api.dto.alternative.AlternativeRequestCreate;
import com.bethunter.bethunter_api.dto.alternative.AlternativeRequestUpdate;
import com.bethunter.bethunter_api.dto.alternative.AlternativeResponse;
import com.bethunter.bethunter_api.exception.QuestionNotFound;
import com.bethunter.bethunter_api.mapper.AlternativeMapper;
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

    @Autowired
    private AlternativeMapper alternativeMapper;

    public AlternativeResponse createAlternative(AlternativeRequestCreate dto) {
        var question = repositoryQuestion.findById(dto.id_question())
                .orElseThrow(() -> new QuestionNotFound());

        Alternative alternative = repositoryAlternative.save(
                alternativeMapper.toEntity(dto, question)
        );

        return alternativeMapper.toResponseDTO(alternative);
    }

    public List<AlternativeResponse> findAll() {
        return repositoryAlternative.findAll().stream()
                .map(alternativeMapper::toResponseDTO)
                .toList();
    }

    public Optional<AlternativeResponse> findById(String id) {
        return repositoryAlternative.findById(id)
                .map(alternativeMapper::toResponseDTO);
    }

    public Optional<AlternativeResponse> update(String id, AlternativeRequestUpdate dto) {
        return repositoryQuestion.findById(dto.id_question())
                .flatMap(question -> repositoryAlternative.findById(id)
                        .map(alt -> {
                            Alternative updated = repositoryAlternative.save(
                                    alternativeMapper.toEntityUpdate(alt, dto, question)
                            );
                            return alternativeMapper.toResponseDTO(updated);
                        })
                );
    }

    public boolean delete(String id) {
        if (repositoryAlternative.existsById(id)) {
            repositoryAlternative.deleteById(id);
            return true;
        }

        return false;
    }

}
