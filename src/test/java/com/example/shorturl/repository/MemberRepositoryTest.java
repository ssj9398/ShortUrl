package com.example.shorturl.repository;

import com.example.shorturl.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach //테스트 시작전 한번씩 실행
    void 데이터준비(){
        String email = "test@google.com";
        String password = "test1234";
        Member member = Member.createMember(email, password);
        memberRepository.save(member);
    }

    @Test
    void findByEmail() {
        //when
        Optional<Member> optMember = memberRepository.findByEmail("test@google.com");

        //then
        assertThat(optMember.get().getEmail()).isEqualTo("test@google.com");
        assertThat(optMember.get().getPassword()).isEqualTo("test1234");
    }

    @Test
    void findByEmailAndPassword() {
    }
}