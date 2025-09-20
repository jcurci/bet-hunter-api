package com.bethunter.bethunter_api.exception;

public class AlreadyAnswered extends RuntimeException{

    public AlreadyAnswered() { super("The user has already answered this question"); }

    public AlreadyAnswered(String message) { super(message); }
}
