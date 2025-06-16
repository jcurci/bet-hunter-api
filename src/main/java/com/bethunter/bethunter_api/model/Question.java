package com.bethunter.bethunter_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "question")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_topic", nullable = false)
    private Topic topic;

    @Column(name = "question_number", nullable = false)
    private int questionNumber;
    private String statement;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Alternative> alternatives;

    public Question(Topic topic, int questionNumber, String statement) {
        this.topic = topic;
        this.questionNumber = questionNumber;
        this.statement = statement;
    }
}
