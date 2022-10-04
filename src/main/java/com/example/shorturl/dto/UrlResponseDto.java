package com.example.shorturl.dto;

import com.example.shorturl.domain.UrlInfo;
import lombok.Getter;

@Getter
public class UrlResponseDto {
    private String realUrl;

    private String fakeUrl;

    private Long visitCount;

    public UrlResponseDto(UrlInfo urlInfo) {
        this.realUrl = urlInfo.getRealUrl();
        this.fakeUrl = "http://url.govpped.com/url/"+urlInfo.getFakeUrl();
        this.visitCount = urlInfo.getVisitCount();
    }
}
