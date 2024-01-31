package com.example.oauthtest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

    private Long memberId;
    private String accessToken;

    @Builder
    private LoginResponse(Long memberId, String accessToken) {
        this.memberId = memberId;
        this.accessToken = accessToken;
    }

    public static LoginResponse of(Long memberId, String accessToken) {
        return builder()
                .memberId(memberId)
                .accessToken(accessToken)
                .build();
    }
    
}
