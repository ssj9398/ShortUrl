package com.example.shorturl.dto;

import com.example.shorturl.domain.UrlInfo;
import lombok.Getter;

public class UrlRequestDto {

    @Getter
    public static class Create{
        private String url;

        public UrlInfo toEntity(String fakeUrl, String realUrl){
            return UrlInfo.builder()
                    .realUrl(realUrl)
                    .fakeUrl(fakeUrl)
                    .visitCount(0L)
                    .build();
        }
    }
}
