package com.example.shorturl.service.url;

import com.example.shorturl.common.advice.exception.ApiRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UrlServiceImplTest {

    @InjectMocks
    private UrlServiceImpl urlService;

    @Test
    @DisplayName("http확인후 없을 시 붙여주기")
    void checkHttp() {
        //given
        String url = "http://naver.com";
        String url2 = "https://naver.com";
        String url3 = "naver.com";

        //when
        String checkHttp = urlService.checkHttp(url);
        String checkHttp2 = urlService.checkHttp(url2);
        String checkHttp3 = urlService.checkHttp(url3);

        //then
        assertThat(checkHttp).contains("http");
        assertThat(checkHttp2).contains("http");
        assertThat(checkHttp3).contains("http");
    }

    @Test
    @DisplayName("주소 유효성 검사")
    void compareDay(){
        //given
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime oneDaysAgo = LocalDateTime.now().minusDays(1);

        //when
        int compareDay = urlService.compareDay(nowTime, oneDaysAgo);

        //then
        assertThat(compareDay).isGreaterThan(0);

    }

    @Test
    @DisplayName("유효한Url")
    void checkValidUrl(){
        //given
        String realUrl = "https://naver.com";

        //when
        String validUrl = urlService.checkValidUrl(realUrl);

        //then
        assertThat(validUrl).isEqualTo(realUrl);
    }

    @Test
    @DisplayName("유효하지않은Url")
    void checkInValidUrl(){
        //given
        String realUrl = "h://n.c";

        //when
        ApiRequestException apiRequestException = Assertions.assertThrows(ApiRequestException.class, () -> {
            urlService.checkValidUrl(realUrl);
        });

        //then
        String message = apiRequestException.getMessage();
        assertEquals("유효하지 않은 주소 입니다.", message);
    }
}