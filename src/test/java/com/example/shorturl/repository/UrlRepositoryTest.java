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
    }
}