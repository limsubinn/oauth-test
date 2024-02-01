package com.example.oauthtest.member.service;

import com.example.oauthtest.auth.model.OAuth2UserInfo;
import com.example.oauthtest.auth.model.OAuth2UserPrincipal;
import com.example.oauthtest.common.utils.JwtTokenProvider;
import com.example.oauthtest.member.dto.LoginResponse;
import com.example.oauthtest.member.entity.Member;
import com.example.oauthtest.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(Authentication authentication) {
        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);

        Member member = findMemberBySocial(principal.getUserInfo());
        String accessToken = jwtTokenProvider.generateAccessToken(authentication, member.getId());

        return LoginResponse.of(member.getId(), accessToken);
    }

    @Transactional
    public Member findMemberBySocial(OAuth2UserInfo info) {
        // DB -> 회원 정보 있는지 확인
        Optional<Member> findMember = memberRepository.findByProviderAndSocialId(
                info.getProvider(), info.getId());

        // 회원 정보가 있으면 해당 회원 리턴
        // 없으면 새로 생성해서 리턴
        return findMember.orElseGet(() -> memberRepository.save(Member.of(info)));
    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2UserPrincipal) {
            return (OAuth2UserPrincipal) principal;
        }
        return null;
    }

}
