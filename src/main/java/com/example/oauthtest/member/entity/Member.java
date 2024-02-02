package com.example.oauthtest.member.entity;

import com.example.oauthtest.oauth.model.OAuth2Provider;
import com.example.oauthtest.oauth.model.OAuth2UserInfo;
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
    private String socialId;

    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp connectedAt;

    @Builder
    private Member(String nickname, String socialId, OAuth2Provider provider, String email, Timestamp connectedAt) {
        this.nickname = nickname;
        this.socialId = socialId;
        this.provider = provider;
        this.email = email;
        this.connectedAt = connectedAt;
    }

    public static Member of(OAuth2UserInfo info) {
        return builder()
                .nickname(info.getProvider().getRegistrationId() + info.getId())
                .socialId(info.getId())
                .provider(info.getProvider())
                .email(info.getEmail())
                .connectedAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }

}
