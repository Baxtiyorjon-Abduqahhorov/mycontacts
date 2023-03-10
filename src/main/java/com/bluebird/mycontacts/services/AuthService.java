package com.bluebird.mycontacts.services;

import com.bluebird.mycontacts.entities.Roles;
import com.bluebird.mycontacts.entities.Users;
import com.bluebird.mycontacts.models.LoginResult;
import com.bluebird.mycontacts.models.RegisterResult;
import com.bluebird.mycontacts.repositories.RolesRepository;
import com.bluebird.mycontacts.repositories.UsersRepository;
import com.bluebird.mycontacts.security.TokenGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@Service
public class AuthService {

    public final TokenGenerator tokenGenerator;

    public final AuthenticationManager authenticationManager;

    public final UsersRepository usersRepository;

    public final RolesRepository rolesRepository;

    public final PasswordEncoder passwordEncoder;

    public final UserInfoService userInfoService;

    private final FileService fileService;

    public AuthService(TokenGenerator tokenGenerator, AuthenticationManager authenticationManager, UsersRepository usersRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder, UserInfoService userInfoService, FileService fileService) {
        this.tokenGenerator = tokenGenerator;
        this.authenticationManager = authenticationManager;
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.userInfoService = userInfoService;
        this.fileService = fileService;
    }

    public ResponseEntity<LoginResult> register(String phone, String password, String firstname, String lastname, MultipartFile picture, String bio) throws IOException {
        if (checkPassAndUsername(phone.trim(), password.trim(), firstname.trim()) != null) {
            return checkPassAndUsername(phone.trim(), password.trim(), firstname.trim());
        }
        if (usersRepository.existsByUsername(phone.trim())) {
            final LoginResult result = new LoginResult(false, "Bu foydalanuvchi allaqachon ro'yhatdan o'tgan.", null, null);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        final Users users = new Users();
        users.setUsername(phone.trim());
        users.setPassword(passwordEncoder.encode(password.trim()));
        final Roles roles = rolesRepository.findByName("ROLE_USER").get();
        users.setRoles(Collections.singleton(roles));

        RegisterResult registerResult = userInfoService.save(firstname.trim(), (lastname != null) ? lastname.trim() : null, picture, phone.trim(), bio).getBody();
        if (!registerResult.getStatus()) {
            final LoginResult result = new LoginResult(false, registerResult.getMessage(), null, null);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        usersRepository.save(users);
        final LoginResult result = login(phone.trim(), password.trim()).getBody();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<LoginResult> login(String phone, String password) {
        if (checkPassAndUsername(phone.trim(), password.trim(), "RUN") != null) {
            return checkPassAndUsername(phone.trim(), password.trim(), "RUN");
        }
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone.trim(), password.trim()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenGenerator.generateToken(authentication);
        final LoginResult result = new LoginResult(true, "Foydalanuvchi tizimga kirdi.", "Bearer", token);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<Boolean> checkUser(String phone) {
        return ResponseEntity.ok(usersRepository.existsByUsername(phone.trim()));
    }

    ResponseEntity<LoginResult> checkPassAndUsername(String phone, String password, String firstname) {
        if (phone.length() != 13 && password.length() < 8) {
            final LoginResult result = new LoginResult(false, "Telefon raqami 13ta belgidan iborat bo'lishi kerak va parol kamida 6ta belgidan iborat bo'lishi kerak.", null, null);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        if (phone.length() != 13) {
            final LoginResult result = new LoginResult(false, "Telefon raqami 13ta belgidan iborat bo'lishi kerak.", null, null);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        if (password.length() < 8 && !stringIsOnlyContainSpace(password)) {
            final LoginResult result = new LoginResult(false, "Parol kamida 8ta belgidan iborat bo'lishi kerak.", null, null);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        if (phone.matches("[0-9]+")) {
            final LoginResult result = new LoginResult(false, "Telefon raqamida faqat '+' va raqamlar ishtirok etishi shart.", null, null);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        if (stringIsOnlyContainSpace(firstname)) {
            final LoginResult result = new LoginResult(false, "Ism noto'g'ri formatda kiritildi.", null, null);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return null;
    }

    boolean stringIsOnlyContainSpace(String str) {
        return str.trim().isEmpty();
    }
}
