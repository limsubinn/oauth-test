package com.example.oauthtest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp connectedAt;

    @Builder
    private Member(String nickname, String email, Timestamp connectedAt) {
        this.nickname = nickname;
        this.email = email;
        this.connectedAt = connectedAt;
    }

    public static Member of(String nickname, String email) {
        return builder()
                .nickname(nickname)
                .email(email)
                .connectedAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }

}
