package com.bluebird.mycontacts.services;

import com.bluebird.mycontacts.entities.UserInfo;
import com.bluebird.mycontacts.models.RegisterResult;
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

    public ResponseEntity<UserInfo> getByPhone(HttpServletRequest request) {
        String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        return ResponseEntity.ok(userInfoRepository.findByPhone(phone).orElse(null));
    }

    public ResponseEntity<RegisterResult> save(String firstName, String lastName, byte[] proPic, String phone, String bio) {
        final UserInfo userInfo = new UserInfo();

        userInfo.setFirst_name(firstName);
        userInfo.setLast_name(lastName);
        userInfo.setPro_pic(proPic);
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
        userInfo.setPro_pic(proPic.getBytes());
        userInfo.setPhone(userInfo.getPhone());
        userInfo.setBio(bio);
        userInfoRepository.save(userInfo);
        return ResponseEntity.ok(new RegisterResult(true, "Updated"));
    }

}
