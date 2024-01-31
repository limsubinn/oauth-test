package com.example.oauthtest.common.exception;

import com.example.oauthtest.common.response.ExceptionResponseStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomBadRequestException extends RuntimeException {

    private final ExceptionResponseStatus status;

}
