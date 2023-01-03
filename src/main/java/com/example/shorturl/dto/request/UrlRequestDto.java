package com.example.shorturl.dto.request;

import com.example.shorturl.domain.UrlInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UrlRequestDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Create{
        private String url;

        private boolean openStatus;

        public UrlInfo toEntity(String fakeUrl){
            return UrlInfo.builder()
                    .realUrl(url)
                    .fakeUrl(fakeUrl)
                    .visitCount(0L)
                    .openStatus(openStatus)
                    .build();
        }
    }
}
