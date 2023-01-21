package com.bluebird.mycontacts.controllers;

import com.bluebird.mycontacts.models.LoginResult;
import com.bluebird.mycontacts.models.RegisterResult;
import com.bluebird.mycontacts.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResult> register(@RequestParam("phone") String phone, @RequestParam("password") String password, @RequestParam("firstname") String firstName, @RequestParam("lastname") String lastName, @RequestParam("bio") String bio, @RequestParam(value = "picture", required = false) String proPic) {
        return authService.register(phone, password, firstName, lastName, proPic, bio);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResult> login(@RequestParam("phone") String phone, @RequestParam("password") String password) {
        return authService.login(phone, password);
    }

    @GetMapping("/checkUser")
    public ResponseEntity<Boolean> checkUser(@RequestParam("phone") String phone) {
        return authService.checkUser(phone);
    }

}
