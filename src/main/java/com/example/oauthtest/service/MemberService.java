package com.example.oauthtest.service;

import com.example.oauthtest.auth.model.OAuth2UserInfo;
import com.example.oauthtest.entity.Member;
import com.example.oauthtest.auth.model.OAuth2Provider;
import com.example.oauthtest.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findMemberBySocial(OAuth2UserInfo info) {
        // DB -> 회원 정보 있는지 확인
        Optional<Member> findMember = memberRepository.findByProviderAndSocialId(
                info.getProvider(), info.getId());

        // 회원 정보가 있으면 해당 회원 리턴
        if (!findMember.isEmpty()) {
            return findMember.get();
        }

        return memberRepository.save(Member.of(info));
    }

}
