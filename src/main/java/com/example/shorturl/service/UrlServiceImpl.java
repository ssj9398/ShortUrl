package com.example.shorturl.service;

import com.example.shorturl.advice.exception.ApiRequestException;
import com.example.shorturl.domain.UrlInfo;
import com.example.shorturl.dto.UrlRequestDto;
import com.example.shorturl.dto.UrlResponseDto;
import com.example.shorturl.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UrlServiceImpl implements UrlService{

    private final UrlRepository urlRepository;

    @Override
    @Transactional
    public String addUrl(UrlRequestDto.Create create) {
            checkValidUrl(create.getUrl());
            return urlRepository.save(create.toEntity(makeFakeUrl())).getFakeUrl();
    }

    @Override
    @Transactional
    public UrlInfo getUrlInfo(String url) {
        if(url.charAt(url.length() - 1)!='*'){
            UrlInfo urlInfo = urlRepository.findByFakeUrl(url).get();
            urlInfo.updateVisitCount();
            return urlRepository.findByFakeUrl(url).get();
        }
        return urlRepository.findByFakeUrl(url.substring(0, url.length()-1)).get();
    }

    @Override
    public List<UrlResponseDto> getTopTenUrlList() {
        return urlRepository.findTop10ByOrderByCreatedAtDesc().stream()
                .map(UrlResponseDto::new)
                .collect(Collectors.toList());
    }

    public String makeFakeUrl(){
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;

        Random random = new Random();
        while (true){
            String randomString = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            Optional<UrlInfo> optUrlInfo = urlRepository.findByFakeUrl(randomString);

            if(optUrlInfo.isEmpty()){
                return randomString;
            }
        }
    }

    public String checkHttp(String url){
        if(url.indexOf("http")==-1){
            return "http://"+url;
        }else {
            return url;
        }
    }

    public String checkValidUrl(String realUrl) {
        try {
            URL url = new URL(checkHttp(realUrl));
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            int statusCode = http.getResponseCode();
            return realUrl;
        }catch (Exception e){
            throw new ApiRequestException("유효하지 않은 주소 입니다.");
        }
    }
}
