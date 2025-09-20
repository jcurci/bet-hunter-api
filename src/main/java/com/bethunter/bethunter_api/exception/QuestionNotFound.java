package com.bethunter.bethunter_api.exception;

public class QuestionNotFound extends RuntimeException {

    public QuestionNotFound() { super("Question not found"); }

    public QuestionNotFound(String message) { super(message); }
}
