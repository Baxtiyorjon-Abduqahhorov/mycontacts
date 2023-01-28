package com.bluebird.mycontacts.controllers;

import com.bluebird.mycontacts.entities.UserInfo;
import com.bluebird.mycontacts.models.AvailableContactResult;
import com.bluebird.mycontacts.services.FindService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/find")
public class FindController {

    private final FindService findService;

    public FindController(FindService findService) {
        this.findService = findService;
    }

    @GetMapping("/check")
    public ResponseEntity<List<AvailableContactResult>> check(HttpServletRequest request) {
        return findService.checker(request);
    }
}
