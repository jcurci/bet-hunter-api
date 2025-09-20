package com.bethunter.bethunter_api.exception;

public class AlternativeNotFound extends RuntimeException{

    public AlternativeNotFound() { super("Alternative not found"); }

    public AlternativeNotFound(String message) { super(message); }
}
