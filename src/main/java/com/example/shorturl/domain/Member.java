package com.example.shorturl.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Builder
    private Member(String email, String password){
        this.email = email;
        this.password = password;
    }

    public static Member createMember(String email, String password){
        return new Member(email, password);
    }

    public void updateMemberPassword(String changePassword) {
        this.password = changePassword;
    }
}
