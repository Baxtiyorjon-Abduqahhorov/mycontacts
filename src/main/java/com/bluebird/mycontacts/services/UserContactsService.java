package com.bluebird.mycontacts.services;

import com.bluebird.mycontacts.entities.UserInfo;
import com.bluebird.mycontacts.entities.UsersContacts;
import com.bluebird.mycontacts.models.ContactObject;
import com.bluebird.mycontacts.models.ListContacts;
import com.bluebird.mycontacts.models.RegisterResult;
import com.bluebird.mycontacts.repositories.UserContactsRepository;
import com.bluebird.mycontacts.repositories.UserInfoRepository;
import com.bluebird.mycontacts.security.TokenGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserContactsService {
    private final UserContactsRepository userContactsRepository;

    private final UserInfoRepository userInfoRepository;

    private final TokenGenerator tokenGenerator;

    public UserContactsService(UserContactsRepository userContactsRepository, UserInfoRepository userInfoRepository, TokenGenerator tokenGenerator) {
        this.userContactsRepository = userContactsRepository;
        this.userInfoRepository = userInfoRepository;
        this.tokenGenerator = tokenGenerator;
    }

    public ResponseEntity<List<UsersContacts>> getAll() {
        return ResponseEntity.ok(userContactsRepository.findAll());
    }

    public ResponseEntity<List<UsersContacts>> getByUserId(HttpServletRequest request) {
        String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        final UserInfo getUser = userInfoRepository.findByPhone(phone).orElse(null);
        return ResponseEntity.ok(userContactsRepository.findByUserInfo(getUser).orElse(null));
    }

    public ResponseEntity<RegisterResult> delete(HttpServletRequest request) {
        String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        final UserInfo getUser = userInfoRepository.findByPhone(phone).orElse(null);
        if (getUser == null) {
            return ResponseEntity.ok(new RegisterResult(false, "Not found"));
        }
        userContactsRepository.deleteById(getUser.getId());
        return ResponseEntity.ok(new RegisterResult(true, "Deleted"));
    }

    public ResponseEntity<RegisterResult> save(ListContacts listContacts, HttpServletRequest request) {
        String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        List<ContactObject> contacts = listContacts.getContacts();
        final UserInfo getUser = userInfoRepository.findByPhone(phone).orElse(null);
        contacts.forEach(contactObject -> {
            final UsersContacts usersContacts = new UsersContacts();
            usersContacts.setUserInfo(getUser);
            usersContacts.setContactNumber(contactObject.getNumber());
            usersContacts.setContactName(contactObject.getName());
            userContactsRepository.save(usersContacts);
        });
        return ResponseEntity.ok(new RegisterResult(true, "Kontaktlar saqlandi."));
    }


}
