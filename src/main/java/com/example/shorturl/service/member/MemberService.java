package com.example.shorturl.service.member;

import com.example.shorturl.domain.Member;
import com.example.shorturl.dto.request.MemberRequestDto;
import com.example.shorturl.dto.response.MemberResponseDto;

public interface MemberService {
    MemberResponseDto addMember(MemberRequestDto.Create create);

    MemberResponseDto loginMember(MemberRequestDto.Login login);
}
