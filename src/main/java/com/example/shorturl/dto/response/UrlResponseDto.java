package com.example.shorturl.dto.response;

import com.example.shorturl.domain.UrlInfo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UrlResponseDto {
    private String realUrl;

    private String fakeUrl;

    private Long visitCount;

    private LocalDateTime createTime;

    public UrlResponseDto(UrlInfo urlInfo) {
        this.realUrl = urlInfo.getRealUrl();
        this.fakeUrl = "http://localhost/url/"+urlInfo.getFakeUrl();
        this.visitCount = urlInfo.getVisitCount();
        this.createTime = urlInfo.getCreatedAt();
    }
}
