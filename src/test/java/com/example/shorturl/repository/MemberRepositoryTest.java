package com.example.shorturl.repository;

import com.example.shorturl.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach //테스트 시작전 한번씩 실행
    public void 데이터준비(){
        String email = "test@google.com";
        String password = "test1234";
        Member member = Member.createMember(email, password);
        memberRepository.save(member);
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findByEmailAndPassword() {
    }
}