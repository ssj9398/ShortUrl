package com.example.shorturl.service;

import com.example.shorturl.advice.exception.ApiRequestException;
import com.example.shorturl.domain.Member;
import com.example.shorturl.dto.MemberRequestDto;
import com.example.shorturl.repository.MemberRepository;
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

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void addMember(MemberRequestDto.Create create) {
        Optional<Member> optMember = memberRepository.findByEmail(create.getEmail());
        if(optMember.isEmpty()){
            Member member = Member.createMember(create.getEmail(), passwordEncoder.encode(create.getPassword()));
            memberRepository.save(member);
        }else {
            throw  new ApiRequestException("이미 존재하는 회원입니다.");
        }

    }

    @Override
    public void loginMember(MemberRequestDto.Login login) {
        Optional<Member> optMember = memberRepository.findByEmail(login.getEmail());
        if(optMember.isEmpty()){
            throw new ApiRequestException("존재하지 않는 회원입니다.");
        } else if (!passwordEncoder.matches(login.getPassword(), optMember.get().getPassword())) {
            throw new ApiRequestException("비밀번호 틀림");
        }
    }
}
