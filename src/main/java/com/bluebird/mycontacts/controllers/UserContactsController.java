package com.bluebird.mycontacts.controllers;

import com.bluebird.mycontacts.entities.UsersContacts;
import com.bluebird.mycontacts.models.ContactObject;
import com.bluebird.mycontacts.models.RegisterResult;
import com.bluebird.mycontacts.services.UserContactsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class UserContactsController {
    private final UserContactsService userContactsService;

    public UserContactsController(UserContactsService userContactsService) {
        this.userContactsService = userContactsService;
    }

    @GetMapping("/getContact")
    public ResponseEntity<List<UsersContacts>> getById(HttpServletRequest request) {
        return userContactsService.getByUserId(request);
    }

    @PostMapping("/sync")
    public ResponseEntity<RegisterResult> sync(@RequestBody List<ContactObject> listContacts, HttpServletRequest request) {
        return userContactsService.update(listContacts, request);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UsersContacts>> getAll() {
        return userContactsService.getAll();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<RegisterResult> delete(HttpServletRequest request) {
        return userContactsService.delete(request);
    }
}
