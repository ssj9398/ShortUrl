package com.example.shorturl.advice;

import com.example.shorturl.advice.exception.ApiRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Fail> handle(ApiRequestException e){
        Fail fail = Fail.builder()
                .msg(e.getMessage())
                .build();
        return new ResponseEntity<>(fail, HttpStatus.OK);
    }
}