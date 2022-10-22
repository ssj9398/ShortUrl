package com.example.shorturl.service;

import com.example.shorturl.dto.MemberRequestDto;

public interface MemberService {
    void addMember(MemberRequestDto.Create create);

    void loginMember(MemberRequestDto.Login login);
}
