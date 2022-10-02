package com.example.shorturl.repository;

import com.example.shorturl.domain.UrlInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlInfo, Long> {
    Optional<UrlInfo> findByFakeUrl(String url);
}
