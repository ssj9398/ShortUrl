package com.example.shorturl.service;

import com.example.shorturl.domain.UrlInfo;
import com.example.shorturl.dto.UrlRequestDto;

public interface UrlService {
    String addUrl(UrlRequestDto.Create create);

    UrlInfo getUrlInfo(String url);
}
