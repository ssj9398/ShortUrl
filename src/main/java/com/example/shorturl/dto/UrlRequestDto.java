package com.example.shorturl.dto;

import com.example.shorturl.domain.UrlInfo;
import lombok.Getter;

public class UrlRequestDto {

    @Getter
    public static class Create{
        private String url;

        private boolean openStatus;

        public UrlInfo toEntity(String fakeUrl, String url){
            return UrlInfo.builder()
                    .realUrl(url)
                    .fakeUrl(fakeUrl)
                    .visitCount(0L)
                    .openStatus(openStatus)
                    .build();
        }
    }
}
