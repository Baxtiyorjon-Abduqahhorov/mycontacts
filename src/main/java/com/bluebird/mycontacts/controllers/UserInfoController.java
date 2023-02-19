package com.bluebird.mycontacts.controllers;

import com.bluebird.mycontacts.entities.UserInfo;
import com.bluebird.mycontacts.extra.AppVariables;
import com.bluebird.mycontacts.models.RegisterResult;
import com.bluebird.mycontacts.models.UserInfoResult;
import com.bluebird.mycontacts.services.FileService;
import com.bluebird.mycontacts.services.UserInfoService;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/info")
public class UserInfoController {

    private final UserInfoService userInfoService;

    private final FileService fileService;

    public UserInfoController(UserInfoService userInfoService, FileService fileService) {
        this.userInfoService = userInfoService;
        this.fileService = fileService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserInfo>> getAll() {
        return userInfoService.getAll();
    }

    @PutMapping("/update")
    public ResponseEntity<RegisterResult> put(HttpServletRequest request, @RequestParam("firstname") String firstName, @RequestParam("lastname") String lastName, @RequestParam(value = "picture", required = false) MultipartFile proPic, @RequestParam("bio") String bio) throws IOException {
        return userInfoService.put(request, firstName, lastName, proPic, bio);
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoResult> getMe(HttpServletRequest request) throws IOException {
        return userInfoService.getByPhone(request);
    }

    @GetMapping("/user")
    public ResponseEntity<UserInfoResult> getUser(@RequestParam("id") Long id) throws IOException {
        return userInfoService.getById(id);
    }

    @GetMapping("/picture/{name}")
    public ResponseEntity<FileUrlResource> getPicture(@PathVariable String name) throws IOException {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename\"" + URLEncoder.encode(name))
                .contentType((Objects.equals(fileService.getFileExtension("\\storage\\" + name), "png")) ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG)
                .body(new FileUrlResource(String.format("%s\\%s", AppVariables.PATH, name)));
    }
}
