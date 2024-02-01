package com.example.oauthtest.auth.handler;

import com.example.oauthtest._common.exception.CustomUnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import static com.example.oauthtest._common.response.ExceptionResponseStatus.SOCIAL_AUTHORIZATION_FAIL;

@Component
@Slf4j
public class OAuth2AuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        // 인증 실패
        throw new CustomUnauthorizedException(SOCIAL_AUTHORIZATION_FAIL);
    }

}