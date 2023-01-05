package com.example.shorturl.service.member;

import com.example.shorturl.common.advice.exception.ApiRequestException;
import com.example.shorturl.domain.Member;
import com.example.shorturl.dto.request.MemberRequestDto;
import com.example.shorturl.dto.response.MemberResponseDto;
import com.example.shorturl.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public MemberResponseDto addMember(MemberRequestDto.Create create) {
        Optional<Member> optMember = memberRepository.findByEmail(create.getEmail());
        if(optMember.isEmpty()){
            Member member = Member.createMember(create.getEmail(), bCryptPasswordEncoder.encode(create.getPassword()));
            return new MemberResponseDto(memberRepository.save(member));
        }else {
            throw  new ApiRequestException("이미 존재하는 회원입니다.");
        }

    }

    @Override
    public MemberResponseDto loginMember(MemberRequestDto.Login login) {
        Optional<Member> optMember = Optional.ofNullable(memberRepository.findByEmail(login.getEmail()).orElseThrow(() -> {
            throw new ApiRequestException("존재하지 않는 회원입니다.");
        }));
        if (!bCryptPasswordEncoder.matches(login.getPassword(), optMember.get().getPassword())) {
            System.out.println(login.getPassword());
            System.out.println(optMember.get().getPassword());
            throw new ApiRequestException("비밀번호 틀림");
        }
        return new MemberResponseDto(optMember.get());
    }
}
