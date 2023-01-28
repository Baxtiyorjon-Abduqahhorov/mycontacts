package com.bluebird.mycontacts.services;

import com.bluebird.mycontacts.entities.UserInfo;
import com.bluebird.mycontacts.extra.AppVariables;
import com.bluebird.mycontacts.models.RegisterResult;
import com.bluebird.mycontacts.models.UserInfoResult;
import com.bluebird.mycontacts.repositories.UserInfoRepository;
import com.bluebird.mycontacts.security.TokenGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Blob;
import java.util.Arrays;
import java.util.List;

@Service
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;

    private final TokenGenerator tokenGenerator;

    private final FileService fileService;

    public UserInfoService(UserInfoRepository userInfoRepository, TokenGenerator tokenGenerator, FileService fileService) {
        this.userInfoRepository = userInfoRepository;
        this.tokenGenerator = tokenGenerator;
        this.fileService = fileService;
    }

    public ResponseEntity<List<UserInfo>> getAll() {
        return ResponseEntity.ok(userInfoRepository.findAll());
    }

    public ResponseEntity<UserInfoResult> getByPhone(HttpServletRequest request) throws IOException {
        final String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        final UserInfo userInfo = userInfoRepository.findByPhone(phone).orElse(null);
        final UserInfoResult userInfoResult = new UserInfoResult();
        if (userInfo != null) {
            userInfoResult.setFirst_name(userInfo.getFirst_name());
            userInfoResult.setLast_name(userInfo.getLast_name());
            userInfoResult.setId(userInfo.getId());
            userInfoResult.setBio(userInfo.getBio());
            userInfoResult.setPhone(userInfo.getPhone());
            userInfoResult.setPicture(AppVariables.IMAGE_SERVER_URL + fileService.getFileName(userInfo.getPro_pic()));
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userInfoResult);
    }

    public ResponseEntity<RegisterResult> save(String firstName, String lastName, MultipartFile proPic, String phone, String bio) throws IOException {
        final UserInfo userInfo = new UserInfo();

        userInfo.setFirst_name(firstName);
        userInfo.setLast_name(lastName);
        userInfo.setPro_pic(fileService.saveFile(proPic));
        userInfo.setPhone(phone);
        userInfo.setBio(bio);

        userInfoRepository.save(userInfo);
        return ResponseEntity.ok(new RegisterResult(true, "Saved"));
    }

    public ResponseEntity<RegisterResult> delete(HttpServletRequest request) {
        String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        final UserInfo userInfo = userInfoRepository.findByPhone(phone).orElse(null);
        userInfoRepository.deleteById(userInfo.getId());
        return ResponseEntity.ok(new RegisterResult(true, "Deleted"));
    }

    public ResponseEntity<RegisterResult> put(HttpServletRequest request, String firstName, String lastName, MultipartFile proPic, String bio) throws IOException {
        String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        final UserInfo userInfo = userInfoRepository.findByPhone(phone).orElse(null);
        userInfo.setId(userInfo.getId());
        userInfo.setFirst_name(firstName);
        userInfo.setLast_name(lastName);
        userInfo.setPro_pic(fileService.saveFile(proPic));
        userInfo.setPhone(userInfo.getPhone());
        userInfo.setBio(bio);
        userInfoRepository.save(userInfo);
        return ResponseEntity.ok(new RegisterResult(true, "Updated"));
    }

}
