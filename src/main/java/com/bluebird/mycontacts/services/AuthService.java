package com.bluebird.mycontacts.services;

import com.bluebird.mycontacts.entities.Roles;
import com.bluebird.mycontacts.entities.Users;
import com.bluebird.mycontacts.extra.LoginResult;
import com.bluebird.mycontacts.extra.RegisterResult;
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

import java.util.Collections;

@Service
public class AuthService {

    public final TokenGenerator tokenGenerator;

    public final AuthenticationManager authenticationManager;

    public final UsersRepository usersRepository;

    public final RolesRepository rolesRepository;

    public final PasswordEncoder passwordEncoder;

    public AuthService(TokenGenerator tokenGenerator, AuthenticationManager authenticationManager, UsersRepository usersRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.tokenGenerator = tokenGenerator;
        this.authenticationManager = authenticationManager;
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<RegisterResult> register(String phone, String password) {
        if (phone.length() != 13) {
            final RegisterResult registerResult = new RegisterResult(false, "Telefon raqami 13ta belgidan iborat bo'lishi kerak.");
            return new ResponseEntity<>(registerResult, HttpStatus.BAD_REQUEST);
        }
        if (password.length() < 6) {
            final RegisterResult registerResult = new RegisterResult(false, "Parol kamida 6ta belgidan iborat bo'lishi kerak.");
            return new ResponseEntity<>(registerResult, HttpStatus.BAD_REQUEST);
        }
        if (phone.matches("[0-9]+")) {
            final RegisterResult registerResult = new RegisterResult(false, "Telefon raqamida faqat '+' va raqamlar ishtirok etishi shart.");
            return new ResponseEntity<>(registerResult, HttpStatus.BAD_REQUEST);
        }
        if (usersRepository.existsByPhone(phone)){
            final RegisterResult registerResult = new RegisterResult(false, "Bu foydalanuvchi allaqachon ro'yhatdan o'tgan.");
            return new ResponseEntity<>(registerResult, HttpStatus.BAD_REQUEST);
        }
        final Users users = new Users();
        users.setPhone(phone);
        users.setPassword(passwordEncoder.encode(password));
        final Roles roles = rolesRepository.findByName("USER").get();
        users.setRoles(Collections.singleton(roles));

        usersRepository.save(users);

        final RegisterResult registerResult = new RegisterResult(true, "Foydalanuvchi ro'yhatdan o'tdi.");
        return new ResponseEntity<>(registerResult, HttpStatus.OK);
    }

    public ResponseEntity<LoginResult> login(String phone, String password) {
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenGenerator.generateToken(authentication);
        final LoginResult loginResult = new LoginResult(true, "Foydalanuvchi tizimga kirdi.");
        return ResponseEntity.ok(loginResult);
    }

}
