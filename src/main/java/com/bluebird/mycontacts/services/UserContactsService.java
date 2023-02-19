package com.bluebird.mycontacts.services;

import com.bluebird.mycontacts.entities.UserInfo;
import com.bluebird.mycontacts.entities.UsersContacts;
import com.bluebird.mycontacts.extra.AppVariables;
import com.bluebird.mycontacts.models.ContactObject;
import com.bluebird.mycontacts.models.ListContacts;
import com.bluebird.mycontacts.models.RegisterResult;
import com.bluebird.mycontacts.repositories.UserContactsRepository;
import com.bluebird.mycontacts.repositories.UserInfoRepository;
import com.bluebird.mycontacts.security.TokenGenerator;
import org.springframework.expression.spel.InternalParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
        List<UsersContacts> list = userContactsRepository.findAll();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).getUserInfo().setPro_pic(AppVariables.IMAGE_SERVER_URL + list.get(i).getUserInfo().getPro_pic());
        }
        return ResponseEntity.ok(list);
    }

    public ResponseEntity<List<UsersContacts>> getByUserId(HttpServletRequest request) {
        String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        final UserInfo getUser = userInfoRepository.findByPhone(phone).orElse(null);
        List<UsersContacts> list = userContactsRepository.findByUserInfo(getUser).orElse(null);
        if (list == null) {
            return ResponseEntity.ok(null);
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).getUserInfo().setPro_pic(AppVariables.IMAGE_SERVER_URL + list.get(i).getUserInfo().getPro_pic());
        }
        return ResponseEntity.ok(list);
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

    public ResponseEntity<RegisterResult> update(List<ContactObject> listContacts, HttpServletRequest request) {

        String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        final UserInfo getUser = userInfoRepository.findByPhone(phone).orElse(null);
        List<UsersContacts> usersContactsListCL = new ArrayList<>();

        listContacts.forEach(contactObject -> {
            final UsersContacts usersContacts = new UsersContacts();
            usersContacts.setUserInfo(getUser);
            usersContacts.setContactNumber(contactObject.getNumber());
            usersContacts.setContactName(contactObject.getName());
            usersContactsListCL.add(usersContacts);
        });
        if(!userContactsRepository.existsByUserInfo(getUser)){
            userContactsRepository.saveAll(usersContactsListCL);
            return ResponseEntity.ok(new RegisterResult(true, "Kontaktlar saqlandi."));
        }
        else if(!listContacts.isEmpty()){
            userContactsRepository.delAllByUserId(getUser.getId());
            userContactsRepository.saveAll(usersContactsListCL);
            return ResponseEntity.ok(new RegisterResult(true, "Kontaktlar yangilandi."));
        }else{
            return ResponseEntity.ok(new RegisterResult(false, "Kontaktlar saqlanmadi "));
        }
    }
}
