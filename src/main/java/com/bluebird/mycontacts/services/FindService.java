package com.bluebird.mycontacts.services;

import com.bluebird.mycontacts.entities.UserInfo;
import com.bluebird.mycontacts.entities.UsersContacts;
import com.bluebird.mycontacts.security.TokenGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class FindService {

    private final UserContactsService userContactsService;

    private final UserInfoService userInfoService;

    private final TokenGenerator tokenGenerator;

    public FindService(UserContactsService userContactsService, UserInfoService userInfoService, TokenGenerator tokenGenerator) {
        this.userContactsService = userContactsService;
        this.userInfoService = userInfoService;
        this.tokenGenerator = tokenGenerator;
    }

    public ResponseEntity<List<UserInfo>> checker(HttpServletRequest request) {
        String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        final UserInfo userInfo = userInfoService.getByPhone(request).getBody();
        final List<UsersContacts> usersContacts = userContactsService.getByUserId(request).getBody();
        final List<UserInfo> userInfoList = userInfoService.getAll().getBody();
        final List<String> userContactNumbers = new ArrayList<>();
        List<UserInfo> availableContacts = new ArrayList<>();

        if (usersContacts != null && !usersContacts.isEmpty() && userInfoList != null) {
            usersContacts.forEach(usersContacts1 -> {
                userContactNumbers.add(usersContacts1.getContactNumber());
            });
            userInfoList.forEach(userInfo1 -> {
                if (userContactNumbers.contains(userInfo1.getPhone())){
                    availableContacts.add(userInfo1);
                }
            });
        }

        return ResponseEntity.ok(availableContacts);
    }
}
