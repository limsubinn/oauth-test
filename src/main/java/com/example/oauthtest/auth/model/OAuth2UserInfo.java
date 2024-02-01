package com.example.oauthtest.auth.model;

public interface OAuth2UserInfo {

    OAuth2Provider getProvider();
    String getId();
    String getEmail();
    String getAccessToken();

}
