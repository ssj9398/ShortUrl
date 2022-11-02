package com.example.shorturl.dto.request;

import lombok.Getter;

public class MemberRequestDto {

    @Getter
    public static class Create{
        private String email;

        private String password;
    }

    @Getter
    public static class Login{
        private String email;

        private String password;
    }
}
