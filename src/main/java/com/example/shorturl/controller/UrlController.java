package com.example.shorturl.controller;

import com.example.shorturl.advice.Success;
import com.example.shorturl.domain.UrlInfo;
import com.example.shorturl.dto.UrlRequestDto;
import com.example.shorturl.dto.UrlResponseDto;
import com.example.shorturl.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UrlController {

    private final UrlService urlService;

    @PostMapping("url")
    public ResponseEntity<String> addUrl(@RequestBody UrlRequestDto.Create create){
        return ResponseEntity.status(HttpStatus.CREATED).body("http://url.govpped.com/url/"+urlService.addUrl(create));
    }

    @GetMapping("url")
    public ResponseEntity<Success> getTopTenUrlList(){
        return new ResponseEntity<>(new Success("최근 등록 된 주소 10개 등록 성공!",urlService.getTopTenUrlList()),HttpStatus.OK);
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
}