package com.bethunter.bethunter_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "article")
@Entity(name = "article")
@NoArgsConstructor
@Getter
@Setter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;

    public Article(String title) {
        this.title = title;
    }
}
