package com.example.shorturl.service.url;

import com.example.shorturl.common.advice.exception.ApiRequestException;
import com.example.shorturl.domain.UrlInfo;
import com.example.shorturl.dto.request.UrlRequestDto;
import com.example.shorturl.dto.response.UrlResponseDto;
import com.example.shorturl.repository.UrlRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlServiceImplTest {

    @InjectMocks
    private UrlServiceImpl urlService;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

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

    @Test
    @DisplayName("중복되지않는 가짜주소 생성테스트")
    void makeFakeUrl() {
        //given

        //when
        List<String> fakeUrlList = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            String fakeUrl = urlService.makeFakeUrl();
            fakeUrlList.add(fakeUrl);
        }
        int listSize = fakeUrlList.size();

        //set으로 중복제거
        List<String> setList = fakeUrlList.stream().distinct().collect(Collectors.toList());

        //then
        assertEquals(listSize, setList.size());
    }

    @Test
    @DisplayName("mysql에 주소저장")
    void addUrlByMysql(){
        //given
        String realUrl = "https://naver.com";
        Boolean openStatus = true;
        String fakeUrl = "fakeUrl";
        UrlRequestDto.Create create = new UrlRequestDto.Create(realUrl, openStatus);

        //stub
        when(urlRepository.save(any())).thenReturn(create.toEntity(fakeUrl));

        //when
        UrlResponseDto urlResponseDto = urlService.addUrlByMysql(create);

        //then
        assertThat(urlResponseDto.getRealUrl()).isEqualTo(realUrl);
        assertThat(urlResponseDto.getFakeUrl()).isEqualTo("http://localhost:8081/url/"+fakeUrl);
    }

    @Test
    @DisplayName("saveUrlByMysql")
    void saveUrlByMysql(){
        //given
        String realUrl = "https://naver.com";
        Boolean openStatus = true;
        String fakeUrl = "fakeUrl";
        UrlRequestDto.Create create = new UrlRequestDto.Create(realUrl, openStatus);

        //stub
        when(urlRepository.save(any())).thenReturn(create.toEntity(fakeUrl));

        //when
        UrlInfo urlInfo = urlService.saveUrlByMysql(create.toEntity(fakeUrl));

        //then
        assertThat(urlInfo.getRealUrl()).isEqualTo(realUrl);
        assertThat(urlInfo.getFakeUrl()).isEqualTo(fakeUrl);
    }

    @Test
    @DisplayName("주소 접속 시 조회수 테스트")
    void getUrlInfo(){
        //given
        String realUrl = "https://naver.com";
        Boolean openStatus = true;
        String fakeUrl = "fakeUrl";

        UrlRequestDto.Create create = new UrlRequestDto.Create(realUrl, openStatus);

        //stub
        when(urlRepository.findByFakeUrl(fakeUrl)).thenReturn(Optional.ofNullable(create.toEntity(fakeUrl)));

        //when
        UrlInfo urlInfo = urlService.getUrlInfo(fakeUrl);

        //then
        assertEquals(urlInfo.getFakeUrl(), fakeUrl);
        assertEquals(urlInfo.getRealUrl(), realUrl);
        assertEquals(urlInfo.isOpenStatus(), openStatus);
        assertEquals(urlInfo.getVisitCount(), 1);
    }
}