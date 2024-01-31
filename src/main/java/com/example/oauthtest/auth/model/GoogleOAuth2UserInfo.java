package com.example.oauthtest.auth.model;

import lombok.Builder;

import java.util.Map;

public class GoogleOAuth2UserInfo implements OAuth2UserInfo {

    private String accessToken;
    private String id;
    private String email;
    private String nickname;

    @Builder
    private GoogleOAuth2UserInfo(String accessToken,String id, String email) {
        this.accessToken = accessToken;
        this.id = id;
        this.email = email;
        this.nickname = id;
    }

    public static GoogleOAuth2UserInfo of(String accessToken, Map<String, Object> attributes) {
        return builder()
                .accessToken(accessToken)
                .id((String) attributes.get("sub"))
                .email((String) attributes.get("email"))
                .build();
    }

    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.GOOGLE;
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
        return nickname;
    }

}