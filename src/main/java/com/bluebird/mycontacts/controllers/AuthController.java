package com.bluebird.mycontacts.controllers;

import com.bluebird.mycontacts.extra.LoginResult;
import com.bluebird.mycontacts.extra.RegisterResult;
import com.bluebird.mycontacts.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResult> register(@RequestParam("phone") String phone, @RequestParam("password") String password) {
        return authService.register(phone, password);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResult> login(@RequestParam("phone") String phone, @RequestParam("password") String password) {
        return authService.login(phone, password);
    }

}
