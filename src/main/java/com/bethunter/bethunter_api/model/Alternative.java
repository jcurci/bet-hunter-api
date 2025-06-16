package com.bethunter.bethunter_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "alternative")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Alternative {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_question", nullable = false)
    private Question question;
    private String text;
    private boolean correct = false;

    public Alternative(Question question, String text, boolean correct) {
        this.question = question;
        this.text = text;
        this.correct = correct;
    }
}
