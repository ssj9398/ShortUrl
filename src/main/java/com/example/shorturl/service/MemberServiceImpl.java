package com.example.shorturl.service;

import com.example.shorturl.domain.Member;
import com.example.shorturl.dto.MemberRequestDto;
import com.example.shorturl.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    
    @Override
    @Transactional
    public void addMember(MemberRequestDto.Create create) {
        Member member = Member.createMember(create.getEmail(), create.getPassword());
        memberRepository.save(member);
    }
}
