package com.bluebird.mycontacts.controllers;

import com.bluebird.mycontacts.models.LoginResult;
import com.bluebird.mycontacts.models.RegisterResult;
import com.bluebird.mycontacts.services.AuthService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RegisterResult> register(@RequestPart String phone, @RequestPart String password) {
        return authService.register(phone, password);
    }

    @PostMapping(value = "/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LoginResult> login(@RequestPart String phone, @RequestPart String password) {
        return authService.login(phone, password);
    }

}
