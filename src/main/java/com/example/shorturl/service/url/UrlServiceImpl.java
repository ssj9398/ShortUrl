package com.example.shorturl.service.url;

import com.example.shorturl.advice.exception.ApiRequestException;
import com.example.shorturl.domain.UrlInfo;
import com.example.shorturl.dto.request.UrlRequestDto;
import com.example.shorturl.dto.response.UrlResponseDto;
import com.example.shorturl.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        String url = checkValidUrl(create.getUrl());
        create.isOpenStatus();
            return urlRepository.save(create.toEntity(makeFakeUrl(), url)).getFakeUrl();
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
        return urlRepository.findTop10ByOpenStatusOrderByCreatedAtDesc(true).stream()
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
        if(url.contains("http")==false){
            return "http://"+url;
        }else {
            return url;
        }
    }

    public String checkValidUrl(String realUrl) {
        try {
            URL url = new URL(checkHttp(realUrl));
            url.openConnection();
            return url.toString();
        }catch (Exception e){
            throw new ApiRequestException("유효하지 않은 주소 입니다.");
        }
    }

    @Scheduled(cron = "0 0 0 * * *") //정시마다
    @Transactional
    public void urlJob() {
        deleteUrlByMoreThanTwoDay();
    }

    private void deleteUrlByMoreThanTwoDay() {
        List<UrlInfo> urlInfoList = urlRepository.findAll();
        urlInfoList.forEach((url -> {
            if(compareDay(LocalDateTime.now(), url.getCreatedAt())>1){
                urlRepository.deleteById(url.getId());
            }
        }));
    }

    public static int compareDay(LocalDateTime date1, LocalDateTime date2) {
        LocalDateTime dayDate1 = date1.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime dayDate2 = date2.truncatedTo(ChronoUnit.DAYS);
        int compareResult = dayDate1.compareTo(dayDate2);
        return compareResult;
    }
}