package com.example.shorturl.dto.request;

import lombok.Getter;
import lombok.Setter;

public class MemberRequestDto {

    @Getter
    @Setter
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
