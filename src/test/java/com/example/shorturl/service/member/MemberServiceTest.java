package com.example.shorturl.service.member;

import com.example.shorturl.domain.Member;
import com.example.shorturl.dto.request.MemberRequestDto;
import com.example.shorturl.dto.response.MemberResponseDto;
import com.example.shorturl.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberServiceImpl memberService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given
        MemberRequestDto.Create member = new MemberRequestDto.Create("test@google.com","1234");
        Member createMember = Member.createMember(member.getEmail(), bCryptPasswordEncoder.encode(member.getPassword()));

        //stub
        when(memberRepository.save(any())).thenReturn(createMember);

        //when
        MemberResponseDto memberResponseDto = memberService.addMember(member);

        //then
        Assertions.assertThat(memberResponseDto.getEmail()).isEqualTo(member.getEmail());
        //Assertions.assertThat(memberResponseDto.getPassword()).isEqualTo(passwordEncoder.encode(member.getPassword()));

    }

//    @Test
//    @DisplayName("로그인")
//    void 로그인(){
//        //given
//        MemberRequestDto.Create member = new MemberRequestDto.Create("test@google.com","1234");
//        Member createMember = Member.createMember(member.getEmail(), bCryptPasswordEncoder.encode(member.getPassword()));
//        System.out.println("c = " + createMember.getPassword());
//        MemberRequestDto.Login login = new MemberRequestDto.Login("test@google.com", "1234");
//        Member loginMember = login.toEntity();
//
//        //stub
//        //when(memberRepository.save(any())).thenReturn(createMember);
////        when(memberRepository.findByEmail(login.getEmail())).thenReturn(Optional.ofNullable(createMember));
//        //when(Member.createMember(create.getEmail(), passwordEncoder.encode(create.getPassword()))).thenReturn(member);
//        //when(bCryptPasswordEncoder.encode(member.getPassword())).thenReturn(String.valueOf(createMember));
//
//        //when
//        //MemberResponseDto memberResponseDto = memberService.loginMember(login);
//
//        //then
//        System.out.println("a = " + createMember.getPassword());
//        System.out.println("a = " + bCryptPasswordEncoder.encode("1234"));
//    }

}