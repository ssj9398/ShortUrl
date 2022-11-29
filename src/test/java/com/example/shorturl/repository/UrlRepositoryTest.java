package com.example.shorturl.repository;

import com.example.shorturl.domain.UrlInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;

    @Test
    @DisplayName("주소저장")
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
    @DisplayName("미로그인_가짜주소로찾기")
    void 미로그인_가짜주소로찾기(){
        //given
        UrlInfo urlInfo = UrlInfo.builder()
                .realUrl("google.com")
                .fakeUrl("localhost:8081/abcdef")
                .openStatus(true)
                .build();
        UrlInfo saveUrlInfo = urlRepository.save(urlInfo);

        //when
        UrlInfo url = urlRepository.findByFakeUrl("localhost:8081/abcdef").get();

        //then
        assertThat(url.getId()).isEqualTo(saveUrlInfo.getId());
        assertThat(url.getFakeUrl()).isEqualTo(urlInfo.getFakeUrl());
        assertThat(url.getRealUrl()).isEqualTo(urlInfo.getRealUrl());
        assertThat(url.getCreatedAt()).isEqualTo(urlInfo.getCreatedAt());
        assertThat(url.getVisitCount()).isEqualTo(urlInfo.getVisitCount());
    }

    @Test
    @DisplayName("탑텐최신순조회")
    @Sql("classpath:db/tableInit.sql")
    void 탑텐최신순조회(){
        //given
        List<UrlInfo> urlList = new ArrayList<>();
        for(int i=11; i>0; i--){
            UrlInfo urlInfo = UrlInfo.builder()
                    .realUrl("google.com"+i)
                    .fakeUrl("localhost:8081/abcdef"+i)
                    .openStatus(true)
                    .build();
            urlList.add(urlInfo);
        }
        List<UrlInfo> urlInfoList = urlRepository.saveAll(urlList);

        //when
        List<UrlInfo> urlTopTenList = urlRepository.findTop10ByOpenStatusOrderByCreatedAtDesc(true);

        //then
        assertThat(urlInfoList.size()).isEqualTo(11);
        assertThat(urlTopTenList.size()).isEqualTo(10);
        assertThat(urlTopTenList.get(0).isOpenStatus()).isEqualTo(urlInfoList.get(0).isOpenStatus());
        assertThat(urlTopTenList.stream().findFirst().get().getId()).isEqualTo(urlInfoList.stream().skip(urlInfoList.size()-1).findFirst().get().getId());
        assertThat(urlTopTenList.stream().findFirst().get().getId()).isEqualTo(11L);
    }

    @Test
    @DisplayName("주소삭제")
    void 주소삭제(){
        //given
        UrlInfo urlInfo = UrlInfo.builder()
                .realUrl("naver.com")
                .fakeUrl("localhost:8081/a")
                .openStatus(true)
                .build();
        UrlInfo saveUrlInfo = urlRepository.save(urlInfo);

        //when
        urlRepository.deleteById(saveUrlInfo.getId());

        //then
        assertThat(urlRepository.findByFakeUrl(saveUrlInfo.getFakeUrl())).isEmpty();
        assertThat(urlRepository.findById(saveUrlInfo.getId())).isEmpty();
    }

    @Test
    void 주소전체조회(){
        //given
        List<UrlInfo> urlInfoList = new ArrayList<>();
        for(int i=0; i<10; i++){
            UrlInfo urlInfo = UrlInfo.builder()
                    .realUrl("naver.com"+i)
                    .fakeUrl("localhost:8081/a"+i)
                    .openStatus(true)
                    .build();
            urlInfoList.add(urlInfo);
        }urlRepository.saveAll(urlInfoList);

        //when
        List<UrlInfo> allUrlList = urlRepository.findAll();

        //then
        assertThat(allUrlList.get(0).getRealUrl()).isEqualTo("naver.com0");
        assertThat(allUrlList.get(0).getFakeUrl()).isEqualTo("localhost:8081/a0");

        assertThat(allUrlList.get(1).getRealUrl()).isEqualTo("naver.com1");
        assertThat(allUrlList.get(1).getFakeUrl()).isEqualTo("localhost:8081/a1");

    }
}