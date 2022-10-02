package com.example.shorturl.service;

import com.example.shorturl.dto.UrlRequestDto;

public interface UrlService {
    String addUrl(UrlRequestDto.Create create);

    String getUrlInfo(String url);
}
