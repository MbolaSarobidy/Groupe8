package com.example.enchere.controller;

import com.example.enchere.entity.Token;
import com.example.enchere.service.TokenService;
import com.example.enchere.value_object.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class LogoutController {

    private TokenService service;

    @DeleteMapping("/logout")
    public ResponseEntity<Message> logout(HttpServletRequest request){
        Token token = service.validateIfExist(request);
        service.delete(token);
        return new ResponseEntity<>(new Message("Log out"), HttpStatus.CREATED);
    }

    @Autowired
    public void setService(TokenService service) {
        this.service = service;
    }
}
