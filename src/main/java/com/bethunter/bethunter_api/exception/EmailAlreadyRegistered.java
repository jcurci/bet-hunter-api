package com.bethunter.bethunter_api.exception;

public class EmailAlreadyRegistered extends RuntimeException{

    public EmailAlreadyRegistered() { super("Email already registered"); }

    public EmailAlreadyRegistered(String message) { super(message); }
}
