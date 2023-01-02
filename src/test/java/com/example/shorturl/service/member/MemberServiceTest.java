package com.example.shorturl.service.member;

import com.example.shorturl.domain.Member;
import com.example.shorturl.dto.request.MemberRequestDto;
import com.example.shorturl.dto.response.MemberResponseDto;
import com.example.shorturl.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberServiceImpl memberService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given
        MemberRequestDto.Create member = new MemberRequestDto.Create("test@google.com","1234");
        Member createMember = Member.createMember(member.getEmail(), passwordEncoder.encode(member.getPassword()));

        //stub
        when(memberRepository.save(any())).thenReturn(createMember);

        //when
        MemberResponseDto memberResponseDto = memberService.addMember(member);

        //then
        Assertions.assertThat(memberResponseDto.getEmail()).isEqualTo(member.getEmail());
        //Assertions.assertThat(memberResponseDto.getPassword()).isEqualTo(passwordEncoder.encode(member.getPassword()));

    }

}