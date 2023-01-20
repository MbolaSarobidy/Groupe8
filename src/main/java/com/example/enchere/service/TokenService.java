package com.example.enchere.service;

import com.example.enchere.entity.Authenticable;
import com.example.enchere.entity.Token;
import com.example.enchere.exception.HttpException;
import com.example.enchere.repository.TokenRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class TokenService {

    private TokenRepository repository;

    public Token validateIfExist(HttpServletRequest request){
        String bearer = request.getHeader("Authorization");
        if(bearer == null)
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Bearer token is expected");
        String tokenValue = bearer.substring(7);
        Token token1 = repository.findTokenByValue(tokenValue);
        if(token1 == null)
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Token is not valid");
        return token1;
    }

    @Transactional
    public Authenticable validate(HttpServletRequest request){
        Token token1 = validateIfExist(request);
        if(!token1.getExpired_at().isAfter(LocalDateTime.now())){
                //delete token expired
            delete(token1);
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Token expired");
        }
        return token1.getAuthenticable();
    }

    public void delete(Token token){
        repository.delete(token);
    }

    public Token generate(Authenticable authenticable){
        Token token = new Token();
        token.setAuthenticable(authenticable);
        token.setValue(authenticable.getId()+"-"+ Hashing.sha256().hashString(authenticable.getUsername() + LocalDateTime.now(), StandardCharsets.UTF_8));
        token.setExpired_at(LocalDateTime.now().plusHours(1));
        return repository.save(token);
    }

    @Autowired
    public void setRepository(TokenRepository repository) {
        this.repository = repository;
    }

    public Authenticable validate(HttpServletRequest request, Long id) {
        Authenticable authenticable = validate(request);
        if(!authenticable.getId().equals(id))
            throw new HttpException(HttpStatus.FORBIDDEN, "This token is not yours, change the bearer token value by yours");
        return authenticable;
    }
}
