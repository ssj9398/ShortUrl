package com.example.shorturl.dto;

import lombok.Getter;

public class MemberRequestDto {

    @Getter
    public static class Create{
        private String email;

        private String password;
    }
}