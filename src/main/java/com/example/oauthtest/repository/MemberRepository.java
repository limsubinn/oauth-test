package com.example.oauthtest.repository;

import com.example.oauthtest.entity.Member;
import com.example.oauthtest.auth.model.OAuth2Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByProviderAndSocialId(OAuth2Provider provider, String socialId);

}
