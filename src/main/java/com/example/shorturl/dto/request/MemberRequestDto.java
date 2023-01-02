package com.example.shorturl.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberRequestDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
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
