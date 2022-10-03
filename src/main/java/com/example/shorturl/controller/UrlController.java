package com.example.shorturl.controller;

import com.example.shorturl.dto.UrlRequestDto;
import com.example.shorturl.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UrlController {

    private final UrlService urlService;

    @PostMapping("url")
    public ResponseEntity<String> addUrl(@RequestBody UrlRequestDto.Create create){
        return ResponseEntity.status(HttpStatus.CREATED).body("http://url.govpped.com/"+urlService.addUrl(create));
    }

    @GetMapping("{url}")
    public void redirectUrl(HttpServletResponse response,
                            @PathVariable String url) throws IOException {
        response.sendRedirect(urlService.getUrlInfo(url));
    }
}