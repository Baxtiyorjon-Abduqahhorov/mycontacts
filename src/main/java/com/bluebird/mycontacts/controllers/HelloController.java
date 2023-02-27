package com.bluebird.mycontacts.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/info/")
public class HelloController {

    @GetMapping("hello/simple")
    public String hello(){

        return "Hello Everybody";
    }
}
