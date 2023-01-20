package com.example.enchere.service;

import com.example.enchere.entity.Authenticable;
import com.example.enchere.entity.Token;
import com.example.enchere.repository.AuthenticableRepository;
import com.example.enchere.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthenticableService {
    private AuthenticableRepository repository;
    private TokenService tokenService;

    public Authenticable find(Authenticable authenticable){
        Authenticable authenticable1 = repository.findAuthenticableByUsernameAndPassword(authenticable.getUsername(), authenticable.getPassword());
        if(authenticable1 == null){
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Authentication invalid");
        }
        return authenticable1;
    }

    public Token authenticate(Authenticable authenticable){
        Authenticable authenticable1 = find(authenticable);
        return tokenService.generate(authenticable1);
    }

    public Token authenticateAdmin(Authenticable authenticable){
        Authenticable authenticable1 = find(authenticable);
        if(!authenticable1.isAdmin()){
            throw new HttpException(HttpStatus.FORBIDDEN, "You don't have accessed");
        }
        return tokenService.generate(authenticable1);
    }

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    public void setRepository(AuthenticableRepository repository) {
        this.repository = repository;
    }
}
