package com.bluebird.mycontacts.services;

import com.bluebird.mycontacts.entities.UserInfo;
import com.bluebird.mycontacts.models.RegisterResult;
import com.bluebird.mycontacts.repositories.UserInfoRepository;
import com.bluebird.mycontacts.security.TokenGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;

    private final TokenGenerator tokenGenerator;

    public UserInfoService(UserInfoRepository userInfoRepository, TokenGenerator tokenGenerator) {
        this.userInfoRepository = userInfoRepository;
        this.tokenGenerator = tokenGenerator;
    }

    public ResponseEntity<List<UserInfo>> getAll() {
        return ResponseEntity.ok(userInfoRepository.findAll());
    }

    public ResponseEntity<UserInfo> getByPhone(HttpServletRequest request) {
        String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        return ResponseEntity.ok(userInfoRepository.findByPhone(phone).orElse(null));
    }

    public ResponseEntity<RegisterResult> save(String firstName, String lastName, String proPic, String phone, String bio) {
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

    public ResponseEntity<RegisterResult> put(HttpServletRequest request, String firstName, String lastName, String proPic, String bio) {
        String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        final UserInfo userInfo = userInfoRepository.findByPhone(phone).orElse(null);
        userInfo.setId(userInfo.getId());
        userInfo.setFirst_name(firstName);
        userInfo.setLast_name(lastName);
        userInfo.setPro_pic(proPic);
        userInfo.setPhone(userInfo.getPhone());
        userInfo.setBio(bio);
        userInfoRepository.save(userInfo);
        return ResponseEntity.ok(new RegisterResult(true, "Updated"));
    }

}
