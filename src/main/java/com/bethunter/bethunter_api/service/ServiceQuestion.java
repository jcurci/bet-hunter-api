package com.bethunter.bethunter_api.service;

import com.bethunter.bethunter_api.dto.alternative.AlternativeQuizzResponse;
import com.bethunter.bethunter_api.dto.alternative.AlternativeRequestUpdate;
import com.bethunter.bethunter_api.dto.question.*;
import com.bethunter.bethunter_api.dto.useranswer.UserAnswerResponse;
import com.bethunter.bethunter_api.exception.*;
import com.bethunter.bethunter_api.infra.security.ServiceToken;
import com.bethunter.bethunter_api.mapper.AlternativeMapper;
import com.bethunter.bethunter_api.mapper.QuestionMapper;
import com.bethunter.bethunter_api.model.Alternative;
import com.bethunter.bethunter_api.model.Question;
import com.bethunter.bethunter_api.model.User;
import com.bethunter.bethunter_api.model.UserAnswer;
import com.bethunter.bethunter_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceQuestion {

    @Autowired
    private RepositoryQuestion repositoryQuestion;

    @Autowired
    private RepositoryTopic repositoryTopic;

    @Autowired
    private ServiceToken serviceToken;

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private RepositoryUserAnswer repositoryUserAnswer;

    @Autowired
    private RepositoryAlternative repositoryAlternative;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private AlternativeMapper alternativeMapper;

    public QuestionResponse createQuestion(QuestionRequestCreate dto) {
        return repositoryTopic.findById(dto.id_topic())
                .map(topic -> {
                    Question question = questionMapper.toEntity(dto, topic);
                    Question saved = repositoryQuestion.save(question);
                    return questionMapper.toResponseDTO(saved);
                })
                .orElseThrow(() -> new TopicNotFound());

    }

    public List<QuestionResponse> findAll() {
        return repositoryQuestion.findAll().stream()
                .map(questionMapper::toResponseDTO)
                .toList();
    }

    public Optional<QuestionResponse> findById(String id) {
        return repositoryQuestion.findById(id)
                .map(questionMapper::toResponseDTO);
    }

    public Optional<QuestionResponse> update(String id, QuestionRequestUpdate dto) {
        return repositoryTopic.findById(dto.id_topic())
                .flatMap(topic ->
                        repositoryQuestion.findById(id)
                                .map(question -> {
                                    questionMapper.toEntity(dto, topic, question);
                                    Question updated = repositoryQuestion.save(question);
                                    return questionMapper.toResponseDTO(updated);
                                })
                );
    }

    public boolean delete(String id) {
        if (repositoryQuestion.existsById(id)) {
            repositoryQuestion.deleteById(id);
            return true;
        }

        return false;
    }

    public List<QuestionQuizzResponse> findUnansweredQuestions(String token, String idTopic) {
        String email = serviceToken.validateToken(token.replace("Bearer ", ""));
        if (email.isEmpty()) {
            throw new InvalidToken();
        }

        User user = repositoryUser.findByEmail(email)
                .orElseThrow(() -> new UserNotFound());

        List<Question> allQuestions = repositoryQuestion.findAllByTopicId(idTopic);

        List<String> answeredQuestionIds = repositoryUserAnswer
                .findByUserIdAndQuestionTopicId(user.getId(), idTopic)
                .stream()
                .map(userAnswer -> userAnswer.getQuestion().getId())
                .toList();

        return allQuestions.stream()
                .filter(q -> !answeredQuestionIds.contains(q.getId()))
                .map(q -> {
                    List<AlternativeQuizzResponse> alternatives = q.getAlternatives().stream()
                            .map(alt -> new AlternativeQuizzResponse(alt.getId(), alt.getText(), alt.isCorrect()))
                            .toList();
                    return new QuestionQuizzResponse(
                            q.getId(),
                            q.getQuestionNumber(),
                            q.getStatement(),
                            alternatives
                    );
                })
                .toList();
    }

    public UserAnswerResponse answerQuestion(String token, String id, QuestionAnswerRequest answer) {
        String email = serviceToken.validateToken(token.replace("Bearer ", ""));
        if (email.isEmpty()) {
            throw new InvalidToken();
        }

        User user = repositoryUser.findByEmail(email).orElseThrow(() -> new UserNotFound());
        Question question = repositoryQuestion.findById(id).orElseThrow(() -> new QuestionNotFound());
        Alternative alternative = repositoryAlternative.findById(answer.id_alternative()).orElseThrow(() -> new AlternativeNotFound());

        boolean alreadyAnswered = repositoryUserAnswer
                .existsByUserIdAndQuestionId(user.getId(), question.getId());

        if (alreadyAnswered) {
            throw new AlreadyAnswered();
        }

        repositoryUserAnswer.save(new UserAnswer(user, question, alternative, alternative.isCorrect()));

        return new UserAnswerResponse(alternative.isCorrect());

    }

}
