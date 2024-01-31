package com.example.oauthtest.auth.model;

import java.util.Map;

public interface OAuth2UserInfo {

    OAuth2Provider getProvider();
    String getId();
    String getEmail();
    String getAccessToken();
    Map<String, Object> getAttributes();

}
