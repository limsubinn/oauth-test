package com.example.oauthtest.auth.model;

import lombok.Builder;

import java.util.Map;

public class GoogleOAuth2UserInfo implements OAuth2UserInfo {

    private OAuth2Provider provider;
    private String id;
    private String email;
    private String accessToken;

    @Builder
    private GoogleOAuth2UserInfo(OAuth2Provider provider, String id, String email, String accessToken) {
        this.provider = provider;
        this.id = id;
        this.email = email;
        this.accessToken = accessToken;
    }

    public static GoogleOAuth2UserInfo of(Map<String, Object> attributes, String accessToken) {
        return builder()
                .provider(OAuth2Provider.GOOGLE)
                .id((String) attributes.get("sub"))
                .email((String) attributes.get("email"))
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