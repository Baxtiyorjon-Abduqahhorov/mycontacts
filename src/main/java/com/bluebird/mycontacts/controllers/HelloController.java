package com.bluebird.mycontacts.controllers;

import com.bluebird.mycontacts.models.SimpleStudent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/info")
public class HelloController {

    @GetMapping("hello/simple")
    public ResponseEntity<SimpleStudent> hello(){
        SimpleStudent student = new SimpleStudent();
        student.setFirstname("Abdujalil");
        student.setLastname("Rahimov");
        student.setAge(24);
        return ResponseEntity.ok(student);
    }
}
