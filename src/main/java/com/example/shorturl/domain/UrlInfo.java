package com.example.shorturl.domain;

import com.example.shorturl.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
    @Column(name = "real_url", nullable = false)
    private String realUrl;

    //만든 주소
    @Column(name = "fake_url")
    private String fakeUrl;

    //방문자수
    @Column(name = "visit_count")
    @ColumnDefault("0")
    private Long visitCount;

    @Column(name = "open_status", nullable = false)
    private boolean openStatus = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public UrlInfo(String realUrl, String fakeUrl, Long visitCount, boolean openStatus) {
        this.realUrl = realUrl;
        this.fakeUrl = fakeUrl;
        this.visitCount = visitCount;
        this.openStatus = openStatus;
    }

    public void updateVisitCount() {
        this.visitCount +=1;
    }
}
