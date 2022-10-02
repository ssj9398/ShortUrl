package com.example.shorturl.dto;

import com.example.shorturl.domain.UrlInfo;
import lombok.Getter;

public class UrlRequestDto {

    @Getter
    public static class Create{
        private String url;

        public UrlInfo toEntity(String fakeUrl){
            return UrlInfo.builder()
                    .realUrl(url)
                    .fakeUrl(fakeUrl)
                    .visitCount(0L)
                    .build();
        }
    }
}
