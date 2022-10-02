package com.example.shorturl.service;

import com.example.shorturl.domain.UrlInfo;
import com.example.shorturl.dto.UrlRequestDto;
import com.example.shorturl.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UrlServiceImpl implements UrlService{

    private final UrlRepository urlRepository;

    @Override
    @Transactional
    public String addUrl(UrlRequestDto.Create create) {
        return urlRepository.save(create.toEntity(makeFakeUrl())).getFakeUrl();
    }

    @Override
    public String getUrlInfo(String url) {
        return urlRepository.findByFakeUrl(url).get().getRealUrl();
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
}
