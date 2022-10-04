package com.example.shorturl.domain;

import com.example.shorturl.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "urlInfo")
public class UrlInfo extends Timestamped {

    @Id
    @Column(name = "urlInfo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //진짜주소
    @Lob
    private String realUrl;

    //만든 주소
    private String fakeUrl;

    //방문자수
    private Long visitCount;

    @Builder
    public UrlInfo(String realUrl, String fakeUrl, Long visitCount) {
        this.realUrl = realUrl;
        this.fakeUrl = fakeUrl;
        this.visitCount = visitCount;
    }

    public void updateVisitCount() {
        this.visitCount +=1;
    }
}
