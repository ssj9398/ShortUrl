package com.example.shorturl.dto.response;

import com.example.shorturl.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class MemberResponseDto {

    private Long memberId;

    private String email;

    public MemberResponseDto(Member member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
    }
}
