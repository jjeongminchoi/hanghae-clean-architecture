package com.hanghae.clean_architecture;

import lombok.Getter;

@Getter
public class ResponseDto<T> {

    private String message;

    private T data;

    public ResponseDto(String message) {
        this.message = message;
    }

    public ResponseDto(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
