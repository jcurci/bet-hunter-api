package com.bethunter.bethunter_api.infra;

import com.bethunter.bethunter_api.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    private ResponseEntity<RestErrorMessage> userNotFoundHandler(UserNotFound exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(TopicNotFound.class)
    private ResponseEntity<RestErrorMessage> topicNotFoundHandler(TopicNotFound exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(QuestionNotFound.class)
    private ResponseEntity<RestErrorMessage> questionNotFoundHandler(QuestionNotFound exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(AlternativeNotFound.class)
    private ResponseEntity<RestErrorMessage> AlternativeNotFoundHandler(QuestionNotFound exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(InvalidToken.class)
    private ResponseEntity<RestErrorMessage> invalidTokenHandler(InvalidToken exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.UNAUTHORIZED, exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(threatResponse);
    }

    @ExceptionHandler(InsufficientPoints.class)
    private ResponseEntity<RestErrorMessage> insufficientPointsHandler(InsufficientPoints exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(AlreadyAnswered.class)
    private ResponseEntity<RestErrorMessage> alreadyAnsweredHandler(AlreadyAnswered exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(EmailAlreadyRegistered.class)
    private ResponseEntity<RestErrorMessage> emailAlreadyRegisteredHandler(EmailAlreadyRegistered exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(PhoneAlreadyRegistered.class)
    private ResponseEntity<RestErrorMessage> phoneAlreadyRegisteredHandler(PhoneAlreadyRegistered exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }


}
