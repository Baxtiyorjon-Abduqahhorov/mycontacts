package com.bluebird.mycontacts.services;

import com.bluebird.mycontacts.extra.AppVariables;
import com.bluebird.mycontacts.models.AvailableContactResult;
import com.bluebird.mycontacts.repositories.UserInfoRepository;
import com.bluebird.mycontacts.security.TokenGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class FindService {

    private final UserInfoRepository userInfoRepository;

    private final TokenGenerator tokenGenerator;

    private final FileService fileService;

    public FindService(UserInfoRepository userInfoRepository, TokenGenerator tokenGenerator, FileService fileService) {
        this.userInfoRepository = userInfoRepository;
        this.tokenGenerator = tokenGenerator;
        this.fileService = fileService;
    }

    public ResponseEntity<List<AvailableContactResult>> checker(HttpServletRequest request) {
        final String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        List<AvailableContactResult> availableContacts = new ArrayList<>();
        for (List<Object> object : userInfoRepository.availableContacts(phone)) {
            String path = object.get(3) == null ? null : AppVariables.IMAGE_SERVER_URL + fileService.getFileName(String.valueOf(object.get(3)));
            final AvailableContactResult availableContactResult = new AvailableContactResult(String.valueOf(object.get(0)), String.valueOf(object.get(1)), String.valueOf(object.get(2)), path, String.valueOf(object.get(4)));
            availableContacts.add(availableContactResult);
        }
        return ResponseEntity.ok(availableContacts);
    }
}
