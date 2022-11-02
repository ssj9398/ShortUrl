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
        //given
        String email = "test2@google.com";
        String password = "test123";
        Member member = Member.createMember(email, password);
        memberRepository.save(member);

        //when
        Optional<Member> optMember = memberRepository.findByEmail(email);

        //then
        assertThat(optMember.get().getEmail()).isEqualTo("test2@google.com");
        assertThat(optMember.get().getPassword()).isEqualTo("test123");
    }

    @Test
    void findByEmailAndPassword() {
        //given
        String email = "test2@google.com";
        String password = "test123";
        Member member = Member.createMember(email, password);
        memberRepository.save(member);

        //when
        Optional<Member> optMember = memberRepository.findByEmailAndPassword(email, password);

        //then
        assertThat(optMember.get().getEmail()).isEqualTo("test2@google.com");
        assertThat(optMember.get().getPassword()).isEqualTo("test123");
    }

    @Test
    void 회원등록(){
        //given
        String email = "test@google.com";
        String password = "test1234";

        //when
        Member member = Member.createMember(email, password);
        Member saveMember = memberRepository.save(member);

        //then
        assertThat(saveMember.getEmail()).isEqualTo(email);
        assertThat(saveMember.getPassword()).isEqualTo(password);

    }

    @Test
    void 회원비밀번호수정(){
        //given
        String email = "test@google.com";
        String password = "test1234";

        String changePassword = "test1234567";

        Member member = Member.createMember(email, password);
        Member saveMember = memberRepository.save(member);

        //when
        saveMember.updateMemberPassword(changePassword);

        //then
        assertThat(saveMember.getPassword()).isEqualTo(changePassword);
    }

    @Test
    void 회원삭제(){
        //given
        String email = "test2@google.com";
        String password = "test1234";

        Member member = Member.createMember(email, password);
        Member saveMember = memberRepository.save(member);

        //when
        memberRepository.deleteById(saveMember.getId());

        //then
        assertThat(memberRepository.findById(saveMember.getId())).isEmpty();
        assertThat(memberRepository.findByEmail(saveMember.getEmail())).isEmpty();
    }
}