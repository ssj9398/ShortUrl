package com.example.shorturl.service.url;

import com.example.shorturl.domain.UrlInfo;
import com.example.shorturl.dto.request.UrlRequestDto;
import com.example.shorturl.repository.UrlRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UrlServiceTest {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UrlServiceImpl urlService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    @DisplayName("mysql로 저장(빌드) 속도 테스트 58초")
    void addUrlByMysql() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        int threadCount = 100000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i=0; i<threadCount; i++){
            executorService.submit(()->{
               try {
                   UrlRequestDto.Create create = new UrlRequestDto.Create("https://www.naver.com",true);
                   urlService.addUrlByMysql(create);
               }finally {
                   latch.countDown();
               }
            });
        }
        latch.await();
        long endTime = System.currentTimeMillis();
        System.out.println("거린시간 = " + (endTime - startTime)/1000+"초");
    }

    @Test
    @DisplayName("redis로 저장 속도 테스트 16초")
    void addUrlByRedis() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        int threadCount = 100000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i=0; i<threadCount; i++){
            executorService.submit(()->{
                try {
                    UrlRequestDto.Create create = new UrlRequestDto.Create("https://www.naver.com",true);
                    urlService.addUrlByRedis(create);
                }finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        long endTime = System.currentTimeMillis();
        System.out.println("거린시간 = " + (endTime - startTime)/1000+"초");
    }
}