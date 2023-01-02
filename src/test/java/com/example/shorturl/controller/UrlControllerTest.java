package com.example.shorturl.controller;

import com.example.shorturl.dto.request.UrlRequestDto;
import com.example.shorturl.dto.response.UrlResponseDto;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        DocumentContext dc = JsonPath.parse(response.getBody());

        Integer statusCode = dc.read("$.code");
        String message = dc.read("$.message");
        String realUrl = dc.read("$.data.realUrl");

        assertThat(message).isEqualTo("url등록 성공");
        assertThat(realUrl).isEqualTo(create.getUrl());
        assertThat(statusCode).isEqualTo(202);
    }

    @Test
    @DisplayName("최근 등록된 주소 10건 조회")
    void 최근_등록된_주소_10건_조회(){
        //given
        for(int i =0; i<10; i++){
            UrlRequestDto.Create create = new UrlRequestDto.Create("https://www.naver.com",true);
            urlRepository.save(create.toEntity("fakeUrl",create.getUrl()));
        }

        //when
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange("/url", HttpMethod.GET, request, String.class);

        //then
        DocumentContext dc = JsonPath.parse(response.getBody());

        Integer statusCode = dc.read("$.code");
        List<UrlResponseDto> urlResponseDto = dc.read("data.topTenUrl");
        String last = dc.read("$.data.topTenUrl[0].createTime");
        String first = dc.read("$.data.topTenUrl[9].createTime");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

        LocalDateTime lastTime = LocalDateTime.parse(last, formatter);
        LocalDateTime firstTime = LocalDateTime.parse(first, formatter);

        assertThat(urlResponseDto.size()).isEqualTo(10);
        assertThat((lastTime)).isAfter(firstTime);
        assertThat(statusCode).isEqualTo(200);
    }
}