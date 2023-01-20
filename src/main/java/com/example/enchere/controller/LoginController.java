package com.example.enchere.controller;

import com.example.enchere.entity.Authenticable;
import com.example.enchere.entity.Token;
import com.example.enchere.service.AuthenticableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LoginController {

    private AuthenticableService service;

    @PostMapping("/login")
    public Token login(@RequestBody Authenticable authenticable, @RequestParam(required = false) boolean admin){
        if(admin)
            return service.authenticateAdmin(authenticable);
        return service.authenticate(authenticable);
    }

    @Autowired
    public void setService(AuthenticableService service) {
        this.service = service;
    }
}
