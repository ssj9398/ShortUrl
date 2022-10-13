package com.example.shorturl.repository;

import com.example.shorturl.domain.UrlInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlInfo, Long> {
    Optional<UrlInfo> findByFakeUrl(String url);

    List<UrlInfo> findTop10ByOpenStatusOrderByCreatedAtDesc(boolean openStatus);
}
