package com.example.shorturl.advice;

import lombok.Getter;

@Getter
public class Success<T> {

    private String result ;
    private String msg;
    private T data;

    public Success( String msg, T data) {
        this.result = "success";
        this.msg = msg;
        this.data = data;
    }
}