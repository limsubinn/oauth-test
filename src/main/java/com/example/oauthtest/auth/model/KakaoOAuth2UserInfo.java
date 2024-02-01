package com.example.oauthtest.auth.model;

import lombok.Builder;

import java.util.Map;
import java.util.UUID;

public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    private OAuth2Provider provider;
    private String id;
    private String email;
    private String accessToken;
    private Map<String, Object> attributes;

    @Builder
    private KakaoOAuth2UserInfo(OAuth2Provider provider, String id, String email, String accessToken) {
        this.provider = provider;
        this.id = id;
        this.email = email;
        this.accessToken = accessToken;
    }

    public static KakaoOAuth2UserInfo of(Map<String, Object> attributes, String accessToken) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        return builder()
                .provider(OAuth2Provider.KAKAO)
                .id(((Long) attributes.get("id")).toString())
                .email((String) kakaoAccount.get("email"))
                .accessToken(accessToken)
                .build();
    }

    @Override
    public OAuth2Provider getProvider() {
        return provider;
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
    public String getAccessToken() {
        return accessToken;
    }

}