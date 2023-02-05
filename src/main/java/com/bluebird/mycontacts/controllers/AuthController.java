package com.bluebird.mycontacts.controllers;

import com.bluebird.mycontacts.models.LoginResult;
import com.bluebird.mycontacts.services.AuthService;
import com.bluebird.mycontacts.services.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    private final FileService fileService;

    public AuthController(AuthService authService, FileService fileService) {
        this.authService = authService;
        this.fileService = fileService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResult> register(@RequestParam("phone") String phone, @RequestParam("password") String password, @RequestParam("firstname") String firstName, @RequestParam(value = "lastname", required = false) String lastName, @RequestParam(value = "bio", required = false) String bio, @RequestParam(value = "picture", required = false) MultipartFile proPic) throws IOException {
        System.out.println(System.getProperty("user.dir").replace("/build/libs",""));
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
