package com.bethunter.bethunter_api.exception;

public class PhoneAlreadyRegistered extends RuntimeException{

    public PhoneAlreadyRegistered() { super("Phone already registered"); }

    public PhoneAlreadyRegistered(String message) { super(message); }
}
