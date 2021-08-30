package com.classpath.accountsapi.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> handleInvalidAccountId(Exception exception){
        return ResponseEntity.status(NOT_FOUND).body(new Error(100, exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleInvalidMethodArgument(MethodArgumentNotValidException exception){
        List<String> errorMessages = exception.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(toList());
        return  ResponseEntity.status(BAD_REQUEST).body(new Error(200, errorMessages));
    }
}

@Setter
@Getter
@NoArgsConstructor
class Error {
    private int code;
    private List<String> messages = new ArrayList<>();


    public Error(int code, String message){
        this.code = code;
        this.messages.add(message);
    }

    public Error(int code, List<String> listOfErrors){
        this.code = code;
        this.messages = listOfErrors;
    }


}