package com.example.shorturl.dto.response;

import com.example.shorturl.domain.UrlInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class UrlResponseDto {
    private String realUrl;

    private String fakeUrl;

    private Long visitCount;

    private LocalDateTime createTime;

    public UrlResponseDto(UrlInfo urlInfo) {
        this.realUrl = urlInfo.getRealUrl();
        this.fakeUrl = "http://localhost:8081/url/"+urlInfo.getFakeUrl();
        this.visitCount = urlInfo.getVisitCount();
        this.createTime = urlInfo.getCreatedAt();
    }

    @Getter
    public static class TopTenUrl{
        List<UrlResponseDto> TopTenUrl;

        public TopTenUrl(List<UrlResponseDto> topTenUrlList) {
            this.TopTenUrl = topTenUrlList;
        }
    }
}
