package com.example.oauthtest.member.repository;

import com.example.oauthtest.member.entity.Member;
import com.example.oauthtest.oauth.model.OAuth2Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByProviderAndSocialId(OAuth2Provider provider, String socialId);
    Optional<Member> findById(Long memberId);

}
