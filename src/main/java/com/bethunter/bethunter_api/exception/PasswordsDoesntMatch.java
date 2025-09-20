package com.bethunter.bethunter_api.exception;

public class PasswordsDoesntMatch extends RuntimeException{

    public PasswordsDoesntMatch() { super("The passwords does not match"); }

    public PasswordsDoesntMatch(String message) { super(message); }
}
