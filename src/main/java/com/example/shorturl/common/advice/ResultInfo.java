package com.example.shorturl.common.advice;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ResultInfo {

    private int code;

    private String message;
    private Object data;


    public enum Code {
        SUCCESS(200),
        FAIL(100),
        CREATE(201);

        int value;

        Code(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public ResultInfo(Code code) {
        this.code = code.getValue();
    }

    public static ResultInfo success() {
        ResultInfo resultInfo = new ResultInfo(Code.SUCCESS);
        return resultInfo;
    }

    public static ResultInfo fail() {
        ResultInfo resultInfo = new ResultInfo(Code.FAIL);
        return resultInfo;
    }

    public ResultInfo(Code code, String message) {
        this.code = code.getValue();
        this.message = message;
    }

    public static ResultInfo success(String message) {
        ResultInfo resultInfo = new ResultInfo(Code.SUCCESS, message);
        return resultInfo;
    }

    public static ResultInfo fail(String message) {
        ResultInfo resultInfo = new ResultInfo(Code.FAIL, message);
        return resultInfo;
    }

    public ResultInfo(Code code, String message, Object data) {
        this.code = code.getValue();
        this.message = message;
        this.data = data;
    }

    public static ResultInfo success(String message, Map<String, Object> result) {
        ResultInfo resultInfo = new ResultInfo(Code.SUCCESS, message, result);
        return resultInfo;
    }

    public static ResultInfo fail(String message, Map<String, Object> result) {
        ResultInfo resultInfo = new ResultInfo(Code.FAIL, message, result);
        return resultInfo;
    }

    public static ResultInfo success(Map<String, Object> result) {
        ResultInfo resultInfo = new ResultInfo(Code.SUCCESS, null, result);
        return resultInfo;
    }
}