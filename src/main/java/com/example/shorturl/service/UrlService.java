package com.example.shorturl.service;

import com.example.shorturl.domain.UrlInfo;
import com.example.shorturl.dto.UrlRequestDto;
import com.example.shorturl.dto.UrlResponseDto;

import java.util.List;

public interface UrlService {
    String addUrl(UrlRequestDto.Create create);

    UrlInfo getUrlInfo(String url);

    List<UrlResponseDto> getTopTenUrlList();
}
