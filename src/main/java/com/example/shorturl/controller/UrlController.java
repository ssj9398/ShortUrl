package com.example.shorturl.controller;

import com.example.shorturl.common.advice.ResultInfo;
import com.example.shorturl.common.advice.Success;
import com.example.shorturl.domain.UrlInfo;
import com.example.shorturl.dto.request.UrlRequestDto;
import com.example.shorturl.dto.response.UrlResponseDto;
import com.example.shorturl.service.url.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UrlController {

    private final UrlService urlService;

    @PostMapping("url")
    public ResultInfo addUrl(@RequestBody UrlRequestDto.Create create) throws IOException {
        return new ResultInfo(ResultInfo.Code.CREATE,"url등록 성공",urlService.addUrlByMysql(create));
    }

    @GetMapping("url")
    public ResponseEntity<Success> getTopTenUrlList(){
        return new ResponseEntity<>(new Success("최근 등록 된 주소 10개 조회 성공!",urlService.getTopTenUrlList()),HttpStatus.OK);
    }

    @GetMapping("url/{url}")
    public UrlResponseDto redirectUrl(HttpServletResponse response,
                                      @PathVariable String url) throws IOException {
        if(url.charAt(url.length() - 1)!='*'){
            response.sendRedirect(urlService.getUrlInfo(url).getRealUrl());
        }else {
            return new UrlResponseDto(urlService.getUrlInfo(url));
        }
        return null;
    }

    @PostMapping("url/redis")
    public ResponseEntity<String> addUrlByRedis(@RequestBody UrlRequestDto.Create create) {
        return ResponseEntity.status(HttpStatus.CREATED).body("http://localhost:8081/url/"+urlService.addUrlByRedis(create));
    }

    @GetMapping("url/redis/{url}")
    public UrlResponseDto redirectUrlByRedis(HttpServletResponse response,
                                      @PathVariable String url) throws IOException {
        if(url.charAt(url.length() - 1)!='*'){
            response.sendRedirect(urlService.getUrlInfoByRedis(url).getRealUrl());
        }else {
            return new UrlResponseDto(urlService.getUrlInfoByRedis(url));
        }
        return null;
    }
}