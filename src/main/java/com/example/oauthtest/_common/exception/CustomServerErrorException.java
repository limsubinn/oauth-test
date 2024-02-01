package com.example.oauthtest._common.exception;

import com.example.oauthtest._common.response.ExceptionResponseStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomServerErrorException extends RuntimeException {

    private final ExceptionResponseStatus status;

}
