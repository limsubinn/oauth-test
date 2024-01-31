package com.example.oauthtest.auth.handler;

import com.example.oauthtest.auth.model.OAuth2Provider;
import com.example.oauthtest.auth.model.OAuth2UserPrincipal;
import com.example.oauthtest.auth.util.JwtTokenProvider;
import com.example.oauthtest.common.response.BaseSuccessResponse;
import com.example.oauthtest.dto.LoginResponse;
import com.example.oauthtest.entity.Member;
import com.example.oauthtest.service.MemberService;
import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 로그인 응답 객체 생성
        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);
        BaseSuccessResponse<?> result = BaseSuccessResponse.of("소셜 로그인에 성공하였습니다.", memberService.login(principal));

        // response
        response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        response.getWriter().println(jsonStr);
    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2UserPrincipal) {
            return (OAuth2UserPrincipal) principal;
        }
        return null;
    }

}