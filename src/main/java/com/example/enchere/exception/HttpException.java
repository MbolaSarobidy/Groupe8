package com.example.enchere.exception;

import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException{
    private HttpStatus status;

    public HttpException(HttpStatus status, String message){
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
