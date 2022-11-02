package com.example.shorturl.repository;

import com.example.shorturl.domain.UrlInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;

    @Test
    void 주소저장(){
        //given
        UrlInfo urlInfo = UrlInfo.builder()
                .realUrl("google.com")
                .fakeUrl("가짜주소")
                .openStatus(true)
                .build();

        //when
        UrlInfo saveUrlInfo = urlRepository.save(urlInfo);

        //then
        assertThat(saveUrlInfo.getFakeUrl()).isEqualTo(urlInfo.getFakeUrl());
        assertThat(saveUrlInfo.getRealUrl()).isEqualTo(urlInfo.getRealUrl());
        assertThat(saveUrlInfo.isOpenStatus()).isEqualTo(urlInfo.isOpenStatus());
    }

    @Test
    void 미로그인_가짜주소로찾기(){
        //given
        UrlInfo urlInfo = UrlInfo.builder()
                .realUrl("google.com")
                .fakeUrl("localhost/abcdef")
                .openStatus(true)
                .build();
        UrlInfo saveUrlInfo = urlRepository.save(urlInfo);

        //when
        UrlInfo url = urlRepository.findByFakeUrl("localhost/abcdef").get();

        //then
        assertThat(url.getId()).isEqualTo(saveUrlInfo.getId());
        assertThat(url.getFakeUrl()).isEqualTo(urlInfo.getFakeUrl());
        assertThat(url.getRealUrl()).isEqualTo(urlInfo.getRealUrl());
        assertThat(url.getCreatedAt()).isEqualTo(urlInfo.getCreatedAt());
        assertThat(url.getVisitCount()).isEqualTo(urlInfo.getVisitCount());
    }
}