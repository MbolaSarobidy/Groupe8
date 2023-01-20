package com.example.enchere.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends HttpException{

    public EntityNotFoundException(Class<?> entity, long id) {
        super(HttpStatus.NOT_FOUND, entity.getSimpleName()+" of id "+id+" not found");
    }
}
