package com.example.shorturl.service.url;

import com.example.shorturl.domain.UrlInfo;
import com.example.shorturl.dto.request.UrlRequestDto;
import com.example.shorturl.dto.response.UrlResponseDto;

import java.io.IOException;
import java.util.List;

public interface UrlService {
    String addUrlByMysql(UrlRequestDto.Create create) throws IOException;

    UrlInfo getUrlInfo(String url);

    List<UrlResponseDto> getTopTenUrlList();

    String addUrlByRedis(UrlRequestDto.Create create);

    UrlInfo getUrlInfoByRedis(String url);
}
