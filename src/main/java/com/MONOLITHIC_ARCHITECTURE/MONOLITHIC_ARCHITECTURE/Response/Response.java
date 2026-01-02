package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Response;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class Response {
    private LocalDateTime timestamp = LocalDateTime.now();
    private Integer statusCode;
    private String message;
    private Object data;
    private Pagination pagination;

    private Response(Integer statusCode, Object data, Pagination pagination, String message) {
        this.statusCode = statusCode;
        this.data = data;
        this.pagination = pagination;
        this.message = message;
    }

    public static Response success(HttpStatus status, Object data) {
        return new Response(status.value(), data, null, null);
    }

    public static Response success(HttpStatus status, Object data, Pagination pagination) {
        return new Response(status.value(), data, pagination, null);
    }

    public static Response error(HttpStatus status, String message) {
        return new Response(status.value(), null, null, message);
    }
}

