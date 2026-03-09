package com.example.bankcards.exception;

import com.example.bankcards.dto.MyErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private MyErrorResponse builder(int status, String message) {

        MyErrorResponse errorResponse = new MyErrorResponse();
        errorResponse.setStatus(status);
        errorResponse.setMessage(message);

        return errorResponse;
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<MyErrorResponse> handleCardNotFound(CardNotFoundException e) {

        return ResponseEntity.status(404).body(builder(404, e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<MyErrorResponse> handleAccessDenied(AccessDeniedException e) {

        return ResponseEntity.status(403).body(builder(403, e.getMessage()));
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<MyErrorResponse> handleInsufficientFunds(InsufficientFundsException e) {

        return ResponseEntity.status(400).body(builder(400, e.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<MyErrorResponse> handleUserNotFoundException(UserNotFoundException e) {

        return ResponseEntity.status(404).body(builder(404, e.getMessage()));
    }

    @ExceptionHandler(CardNotActiveException.class)
    public ResponseEntity<MyErrorResponse> handlerCardNotActiveException(CardNotActiveException e) {

        return ResponseEntity.status(400).body(builder(400, e.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<MyErrorResponse> handlerInvalidCredentialsException(InvalidCredentialsException e) {

        return ResponseEntity.status(400).body(builder(400, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MyErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        return ResponseEntity.status(400).body(builder(400, e.getBindingResult().getFieldErrors().get(0).getDefaultMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MyErrorResponse> handleGeneral(Exception e) {

        return ResponseEntity.status(500).body(builder(500, "Internal server error"));

    }
}