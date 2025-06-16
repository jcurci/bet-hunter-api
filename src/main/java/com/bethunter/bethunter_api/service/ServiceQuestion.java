package com.bethunter.bethunter_api.service;

import com.bethunter.bethunter_api.dto.alternative.AlternativeQuizzResponse;
import com.bethunter.bethunter_api.dto.alternative.AlternativeRequestUpdate;
import com.bethunter.bethunter_api.dto.question.*;
import com.bethunter.bethunter_api.dto.useranswer.UserAnswerResponse;
import com.bethunter.bethunter_api.infra.security.ServiceToken;
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

    public ResponseEntity<QuestionResponse> createQuestion(QuestionRequestCreate dto) {
        var topic = repositoryTopic.findById(dto.id_topic());

        if (topic.isPresent()) {
            Question question = repositoryQuestion.save(new Question(topic.get(), dto.question_number(), dto.statement()));
            return ResponseEntity.status(201).body(new QuestionResponse(question.getId(), question.getTopic().getId(), question.getQuestionNumber(), question.getStatement()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<Question> findAll() {
        return repositoryQuestion.findAll();
    }

    public Optional<Question> findById(String id) {
        return repositoryQuestion.findById(id);
    }

    public Optional<Question> update(String id, QuestionRequestUpdate dto) {
        var topic = repositoryTopic.findById(dto.id_topic());
        if (topic.isPresent()) {
            repositoryQuestion.findById(id)
                    .map(question -> {
                        question.setTopic(topic.get());
                        question.setQuestionNumber(dto.question_number());
                        question.setStatement(dto.statement());
                        return repositoryQuestion.save(question);
                    });
        }

        return null;
    }

    public boolean delete(String id) {
        if (repositoryQuestion.existsById(id)) {
            repositoryQuestion.deleteById(id);
            return true;
        }

        return false;
    }

    public ResponseEntity<List<QuestionQuizzResponse>> findUnansweredQuestions(String token, String idTopic) {
        String email = serviceToken.validateToken(token.replace("Bearer ", ""));
        if (email.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = repositoryUser.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Question> allQuestions = repositoryQuestion.findAllByTopicId(idTopic);

        List<String> answeredQuestionIds = repositoryUserAnswer
                .findByUserIdAndQuestionTopicId(user.getId(), idTopic)
                .stream()
                .map(userAnswer -> userAnswer.getQuestion().getId())
                .toList();

        List<QuestionQuizzResponse> result = allQuestions.stream()
                .filter(question -> !answeredQuestionIds.contains(question.getId()))
                .map(question -> {
                    List<AlternativeQuizzResponse> alternatives = question.getAlternatives().stream()
                            .map(alt -> new AlternativeQuizzResponse(alt.getId(), alt.getText(), alt.isCorrect()))
                            .toList();
                    return new QuestionQuizzResponse(
                            question.getId(),
                            question.getQuestionNumber(),
                            question.getStatement(),
                            alternatives
                    );
                }).toList();

        return ResponseEntity.ok(result);
    }

    public ResponseEntity<?> answerQuestion(String token, String id, QuestionAnswerRequest answer) {
        String email = serviceToken.validateToken(token.replace("Bearer ", ""));
        if (email.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = repositoryUser.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Question question = repositoryQuestion.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));
        Alternative alternative = repositoryAlternative.findById(answer.id_alternative()).orElseThrow(() -> new RuntimeException("Alternative not found"));

        boolean isCorrect = alternative.isCorrect();

        boolean alreadyAnswered = repositoryUserAnswer
                .existsByUserIdAndQuestionId(user.getId(), question.getId());

        if (alreadyAnswered) {
            return ResponseEntity.status(409).body("You already answered this question.");
        }

        repositoryUserAnswer.save(new UserAnswer(user, question, alternative, isCorrect));

        return ResponseEntity.ok(new UserAnswerResponse(isCorrect));

    }

}
