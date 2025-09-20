package com.bethunter.bethunter_api.exception;

public class InvalidToken extends RuntimeException{

    public InvalidToken() { super("Invalid token or has been expired"); }

    public InvalidToken(String message) { super(message); }
}
