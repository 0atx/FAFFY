package com.faffy.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;


/**
 * 컨트롤러에서 발생한 예외를 모아서 처리하는 곳
 */

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity handleDataIntegrityException(DataIntegrityException e) {
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("msg",e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
    }
    @ExceptionHandler(IllegalInputException.class)
    public ResponseEntity handleIllegalInputException(IllegalInputException e) {
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("msg",e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("msg",e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity handleDataNotFoundException(DataNotFoundException e) {
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("msg",e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException e) {
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("msg",e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
    }

    /**
     * 유효성 검사
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");
            builder.append(" | ");
        }
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("msg",builder.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hashmap);
    }

}