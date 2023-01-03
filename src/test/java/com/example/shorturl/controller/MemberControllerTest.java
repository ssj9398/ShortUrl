package com.example.shorturl.controller;

import com.example.shorturl.domain.Member;
import com.example.shorturl.dto.request.MemberRequestDto;
import com.example.shorturl.dto.request.UrlRequestDto;
import com.example.shorturl.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private static ObjectMapper objectMapper;
    private static HttpHeaders headers;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeAll
    public static void init(){
        objectMapper = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @DisplayName("회원가입")
    void 회원가입() throws JsonProcessingException {
        //given
        MemberRequestDto.Create create = new MemberRequestDto.Create("test@google.com", "123456");
        String body = objectMapper.writeValueAsString(create);

        //when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange("/api/member/join", HttpMethod.POST, request, String.class);

        //then
        System.out.println("response = " + response);
        DocumentContext dc = JsonPath.parse(response.getBody());

        Integer statusCode = dc.read("$.code");
        String message = dc.read("$.message");
        String email = dc.read("$.data.email");

        assertThat(message).isEqualTo("회원가입 완료");
        assertThat(email).isEqualTo(create.getEmail());
        assertThat(statusCode).isEqualTo(201);
    }

    @Test
    @DisplayName("로그인")
    void 로그인() throws JsonProcessingException {
        //given
        //회원가입
        MemberRequestDto.Create create = new MemberRequestDto.Create("test2@google.com", "123456");
        Member member = Member.createMember(create.getEmail(), passwordEncoder.encode(create.getPassword()));
        memberRepository.save(member);

        MemberRequestDto.Login login = new MemberRequestDto.Login("test2@google.com", "123456");
        String body = objectMapper.writeValueAsString(login);

        //when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange("/api/member/login", HttpMethod.POST, request, String.class);

        //then
        System.out.println("response = " + response);
        DocumentContext dc = JsonPath.parse(response.getBody());

        Integer statusCode = dc.read("$.code");
        String message = dc.read("$.message");
        String email = dc.read("$.data.email");

        assertThat(message).isEqualTo("로그인 성공");
        assertThat(email).isEqualTo(create.getEmail());
        assertThat(statusCode).isEqualTo(200);
    }
}