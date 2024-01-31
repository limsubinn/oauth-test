package com.example.oauthtest.auth.model;

public interface OAuth2UserInfo {

    OAuth2Provider getProvider();
    String getAccessToken();
    String getId();
    String getEmail();
    String getNickname();

}
