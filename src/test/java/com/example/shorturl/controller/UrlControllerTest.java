package com.example.shorturl.controller;

import com.example.shorturl.dto.request.UrlRequestDto;
import com.example.shorturl.repository.UrlRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrlControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UrlRepository urlRepository;

    private static ObjectMapper objectMapper;
    private static HttpHeaders headers;

    @BeforeAll
    public static void init(){
        objectMapper = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @DisplayName("단축Url_등록")
    void 단축Url_등록() throws JsonProcessingException {
        //given
        UrlRequestDto.Create create = new UrlRequestDto.Create("https://www.naver.com",true);
        String body = objectMapper.writeValueAsString(create);

        //when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange("/url", HttpMethod.POST, request, String.class);

        //then
        System.out.println("rr = " + response);
        DocumentContext dc = JsonPath.parse(response.getBody());

        Integer statusCode = dc.read("$.code");
        String message = dc.read("$.message");
        String realUrl = dc.read("$.data.realUrl");

        assertThat(message).isEqualTo("url등록 성공");
        assertThat(realUrl).isEqualTo(create.getUrl());
        assertThat(statusCode).isEqualTo(202);
    }
}