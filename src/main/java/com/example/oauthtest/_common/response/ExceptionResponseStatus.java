package com.example.oauthtest._common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionResponseStatus {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 요청입니다."),
    URL_NOT_FOUND(HttpStatus.BAD_REQUEST, "유효하지 않은 URL 입니다."),
    METHOD_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "해당 URL에서 지원하지 않는 HTTP Method 입니다."),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 오류가 발생하였습니다."),

    PROVIDER_NOT_SUPPORTED(HttpStatus.UNAUTHORIZED, "지원되지 않는 소셜 플랫폼 입니다."),
    EMAIL_NOT_FOUND(HttpStatus.UNAUTHORIZED, "해당 소셜 플랫폼에 해당하는 이메일이 없습니다."),
    SOCIAL_AUTHORIZATION_FAIL(HttpStatus.UNAUTHORIZED, "소셜 로그인 인증에 실패했습니다."),

    JWT_ERROR(HttpStatus.UNAUTHORIZED, "JWT에서 오류가 발생하였습니다."),
    TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "토큰이 HTTP Header에 없습니다."),
    UNSUPPORTED_TOKEN_TYPE(HttpStatus.BAD_REQUEST, "지원되지 않는 토큰 형식입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 올바르게 구성되지 않았습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    TOKEN_MISMATCH(HttpStatus.UNAUTHORIZED, "요청 정보가 토큰 정보와 일치하지 않습니다."),

    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당하는 멤버 정보가 없습니다."),
    ;

    private final HttpStatus status;
    private final String message;

}
