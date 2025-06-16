package com.bethunter.bethunter_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_answer")
@NoArgsConstructor
@Getter
@Setter
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_question", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_alternative")
    private Alternative alternative;
    private Boolean correct;

    public UserAnswer(User user, Question question, Alternative alternative, Boolean correct) {
        this.user = user;
        this.question = question;
        this.alternative = alternative;
        this.correct = correct;
    }
}
