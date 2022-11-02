package com.example.shorturl.service.member;

import com.example.shorturl.dto.request.MemberRequestDto;

public interface MemberService {
    void addMember(MemberRequestDto.Create create);

    void loginMember(MemberRequestDto.Login login);
}
