package com.example.shorturl.service.url;

import com.example.shorturl.domain.UrlInfo;
import com.example.shorturl.dto.request.UrlRequestDto;
import com.example.shorturl.dto.response.UrlResponseDto;

import java.io.IOException;
import java.util.List;

public interface UrlService {
    UrlResponseDto addUrlByMysql(UrlRequestDto.Create create) throws IOException;

    UrlInfo getUrlInfo(String url);

    UrlResponseDto.TopTenUrl getTopTenUrlList();

    String addUrlByRedis(UrlRequestDto.Create create);

    UrlInfo getUrlInfoByRedis(String url);
}
