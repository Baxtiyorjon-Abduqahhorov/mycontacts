package com.bluebird.mycontacts.controllers;

import com.bluebird.mycontacts.entities.UserInfo;
import com.bluebird.mycontacts.models.RegisterResult;
import com.bluebird.mycontacts.services.UserInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/info")
public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserInfo>> getAll() {
        return userInfoService.getAll();
    }

    @PutMapping("/update")
    public ResponseEntity<RegisterResult> put(HttpServletRequest request, @RequestParam("firstname") String firstName, @RequestParam("lastname") String lastName, @RequestParam(value = "picture", required = false) String proPic, @RequestParam("bio") String bio) {
        return userInfoService.put(request, firstName, lastName, proPic, bio);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<RegisterResult> delete(HttpServletRequest request) {
        return userInfoService.delete(request);
    }
}
