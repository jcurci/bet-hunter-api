package com.bethunter.bethunter_api.exception;

public class InsufficientPoints extends IllegalArgumentException{
    public InsufficientPoints() { super("Not enough points to spin the roulette"); }

    public InsufficientPoints(String message) { super(message); }
}
