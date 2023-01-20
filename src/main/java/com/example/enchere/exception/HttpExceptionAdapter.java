package com.example.enchere.exception;

public class HttpExceptionAdapter {
    private String message;
    private String status;
    private int code;

    public HttpExceptionAdapter(HttpException httpException){
        setStatus(httpException.getStatus().name());
        setCode(httpException.getStatus().value());
        setMessage(httpException.getMessage());
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
