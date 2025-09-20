package com.bethunter.bethunter_api.exception;

public class TopicNotFound extends RuntimeException{
    public TopicNotFound() { super("Topic not found"); }

    public TopicNotFound(String message) { super(message); }
}
