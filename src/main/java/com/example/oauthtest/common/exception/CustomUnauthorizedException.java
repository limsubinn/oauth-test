package com.example.oauthtest.common.exception;

import com.example.oauthtest.common.response.ExceptionResponseStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomUnauthorizedException extends RuntimeException {

    private final ExceptionResponseStatus status;

}