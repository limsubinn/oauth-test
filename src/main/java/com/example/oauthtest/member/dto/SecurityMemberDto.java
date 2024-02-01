package com.example.oauthtest.member.dto;

import com.example.oauthtest.auth.model.OAuth2Provider;
import com.example.oauthtest.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SecurityMemberDto {

    private OAuth2Provider provider;
    private String id;
    private String email;

    @Builder
    private SecurityMemberDto(OAuth2Provider provider, String id, String email) {
        this.provider = provider;
        this.id = id;
        this.email = email;
    }

    public SecurityMemberDto of(Member member) {
        return builder()
                .provider(provider)
                .id(member.getSocialId())
                .email(member.getEmail())
                .build();
    }

}
