package com.example.postoffice.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError<T> {
    private HttpStatus httpStatus;
    private T body;
    private LocalDateTime time;

    public ApiError(HttpStatus httpStatus, T body) {
        this.httpStatus = httpStatus;
        this.body = body;
        this.time = LocalDateTime.now();
    }
}
