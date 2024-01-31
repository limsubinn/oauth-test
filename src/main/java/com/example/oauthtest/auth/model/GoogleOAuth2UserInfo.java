package com.example.oauthtest.auth.model;

import lombok.Builder;

import java.util.Map;

public class GoogleOAuth2UserInfo implements OAuth2UserInfo {

    private OAuth2Provider provider = OAuth2Provider.GOOGLE;
    private String id;
    private String email;
    private String accessToken;
    private Map<String, Object> attributes;

    @Builder
    private GoogleOAuth2UserInfo(Map<String, Object> attributes, String id, String email, String accessToken) {
        this.attributes = attributes;
        this.id = id;
        this.email = email;
        this.accessToken = accessToken;
    }

    public static GoogleOAuth2UserInfo of(Map<String, Object> attributes, String accessToken) {
        return builder()
                .attributes(attributes)
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

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

}