package com.example.oauthtest.service;

import com.example.oauthtest.auth.model.OAuth2UserInfo;
import com.example.oauthtest.auth.model.OAuth2UserPrincipal;
import com.example.oauthtest.auth.util.JwtTokenProvider;
import com.example.oauthtest.common.response.BaseSuccessResponse;
import com.example.oauthtest.dto.LoginResponse;
import com.example.oauthtest.entity.Member;
import com.example.oauthtest.auth.model.OAuth2Provider;
import com.example.oauthtest.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(OAuth2UserPrincipal principal) {
        Member member = findMemberBySocial(principal.getUserInfo());
        String accessToken = jwtTokenProvider.generateAccessToken(principal, member.getId());

        LoginResponse loginResponse = LoginResponse.of(member.getId(), accessToken);
        return LoginResponse.of(member.getId(), accessToken);
    }

    @Transactional
    public Member findMemberBySocial(OAuth2UserInfo info) {
        // DB -> 회원 정보 있는지 확인
        Optional<Member> findMember = memberRepository.findByProviderAndSocialId(
                info.getProvider(), info.getId());

        // 회원 정보가 있으면 해당 회원 리턴
        return findMember.orElseGet(() -> memberRepository.save(Member.of(info)));
    }

}
