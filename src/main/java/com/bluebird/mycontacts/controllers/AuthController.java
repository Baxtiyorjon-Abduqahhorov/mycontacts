package com.bluebird.mycontacts.controllers;

import com.bluebird.mycontacts.models.LoginResult;
import com.bluebird.mycontacts.services.AuthService;
import com.bluebird.mycontacts.services.FileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<LoginResult> register(@RequestParam("phone") String phone, @RequestParam("password") String password, @RequestParam("firstname") String firstName, @RequestParam("lastname") String lastName, @RequestParam("bio") String bio, @RequestParam(value = "picture", required = false) String proPic) {
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

    @PostMapping("/upload")
    public ResponseEntity<String > upload(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        String path = "/storage/" + fileService.saveFile(file);
        return ResponseEntity.ok("Yes");
    }
    @PostMapping("/preview")
    public ResponseEntity<byte[]> preview(@RequestParam("name") String name) throws IOException, InterruptedException {
        InputStream in = getClass().getResourceAsStream("/storage/" + name);
        System.out.println(in);
        return ResponseEntity.ok()
                .contentType((fileService.getFileExtension("/storage/" + name) == "png")?MediaType.IMAGE_PNG:MediaType.IMAGE_JPEG)
                .body(in.readAllBytes());
    }

}
