package com.example.oauthtest.auth.model;

import lombok.Builder;

import java.util.Map;
import java.util.UUID;

public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    private String accessToken;
    private String id;
    private String email;
    private String nickname;

    @Builder
    private KakaoOAuth2UserInfo(String accessToken, String id, String email) {
        this.accessToken = accessToken;
        this.id = id;
        this.email = email;
        this.nickname = id;
    }

    public static KakaoOAuth2UserInfo of(String accessToken, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        return builder()
                .accessToken(accessToken)
                .id(((Long) attributes.get("id")).toString())
                .email((String) kakaoAccount.get("email"))
                .build();
    }

    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.KAKAO;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getNickname() {
        return null;
    }

}