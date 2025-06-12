package com.bethunter.bethunter_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String login;
    private String password;
    private String name;
    private String cellphone;
    private String email;
    private int points;
    private int rank;


}
