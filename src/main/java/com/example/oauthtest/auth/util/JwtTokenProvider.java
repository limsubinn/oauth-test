package com.example.oauthtest.auth.util;

import com.example.oauthtest.auth.model.OAuth2Provider;
import com.example.oauthtest.auth.model.OAuth2UserInfo;
import com.example.oauthtest.auth.model.OAuth2UserInfoFactory;
import com.example.oauthtest.auth.model.OAuth2UserPrincipal;
import com.example.oauthtest.common.exception.CustomBadRequestException;
import com.example.oauthtest.common.exception.CustomUnauthorizedException;
import com.example.oauthtest.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.oauthtest.common.response.ExceptionResponseStatus.*;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider implements InitializingBean {

    @Value("${secret.jwt-secret-key}")
    private String JWT_SECRET_KEY;

    @Value("${secret.jwt-expired-in}")
    private long JWT_EXPIRED_IN;

    private Key key;

    private final long accessTokenValidTime = (60 * 1000) * 30; // 30분
//    private final long refreshTokenValidTime = (60 * 1000) * 60 * 24 * 7; // 7일
    private final String AUTHORITIES_KEY = "auth";

    private final MemberRepository memberRepository;

    @PostConstruct
    protected void init() {
        String encodedKey = Base64.getEncoder().encodeToString(JWT_SECRET_KEY.getBytes());
        key = Keys.hmacShaKeyFor(encodedKey.getBytes());
    }

    public String generateToken(OAuth2UserPrincipal principal, Long memberId, Long accessTokenValidTime) {
        OAuth2Provider provider = principal.getUserInfo().getProvider();
        String socialId = principal.getUserInfo().getId();
        String email = principal.getUsername();

        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenValidTime);

        return Jwts.builder()
                .setSubject(provider.ordinal() + socialId)
                .claim("registrationId", provider.getRegistrationId())
                .claim("accessToken", principal.getUserInfo().getAccessToken())
                .claim("memberId", memberId)
                .claim("email", email)
                .claim("attributes", principal.getUserInfo().getAttributes())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessToken(OAuth2UserPrincipal principal, Long memberId) {
        return generateToken(principal, memberId, accessTokenValidTime);
    }

//    public String generateRefreshToken(OAuth2UserPrincipal principal) {
//        return generateToken(principal, refreshTokenValidTime);
//    }

    public OAuth2UserPrincipal getOAuthPrincipal(String token) {
        // token 으로부터 Claim 가져오기
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo((String) claims.get("registrationId"), (Map<String, Object>) claims.get("attributes"), (String) claims.get("accessToken"));
        return OAuth2UserPrincipal.of(userInfo);
    }

    public boolean isExpiredToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key).build()
                    .parseClaimsJws(token);
            return claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (UnsupportedJwtException e) {
            throw new CustomUnauthorizedException(UNSUPPORTED_TOKEN_TYPE);
        } catch (MalformedJwtException e) {
            throw new CustomUnauthorizedException(MALFORMED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new CustomUnauthorizedException(INVALID_TOKEN);
        } catch (JwtException e) {
            log.error("[JwtTokenProvider.validateAccessToken]", e);
            throw e;
        }
    }

    public JwtCode validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return JwtCode.ACCESS;
        } catch (ExpiredJwtException e) {
            return JwtCode.EXPIRED;
        } catch (Exception e) {
            return JwtCode.DENIED;
        }
    }

    public String getSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.key = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public Long getMemberId(String token) {
        Claims claims = getClaims(token);
        return Long.parseLong(claims.get("memberId", String.class));
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            throw new CustomUnauthorizedException(INVALID_TOKEN);
        } catch (MalformedJwtException e) {
            throw new CustomUnauthorizedException(INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new CustomUnauthorizedException(EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new CustomUnauthorizedException(UNSUPPORTED_TOKEN_TYPE);
        } catch (IllegalArgumentException e) {
            throw new CustomBadRequestException(JWT_ERROR);
        }
    }

    public Long getMemberIdFromExpiredToken(String token) {
        try {
            return getMemberId(token);
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에서 사용자 ID를 추출
            // access token 이 만료되었지만 refresh token 이 존재하는 경우
            Claims expiredClaims = e.getClaims();
            return Long.parseLong(expiredClaims.get("memberId", String.class));
        }
    }

}