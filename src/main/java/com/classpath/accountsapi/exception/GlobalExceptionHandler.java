package com.classpath.accountsapi.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> handleInvalidAccountId(Exception exception){
        return ResponseEntity.status(NOT_FOUND).body(new Error(100, exception.getMessage()));
    }
}

@Setter
@Getter
@NoArgsConstructor
class Error {
    private int code;
    private String message;

    public Error(int code, String message){
        this.code = code;
        this.message = message;
    }


}