package com.bluebird.mycontacts.services;

import com.bluebird.mycontacts.entities.Roles;
import com.bluebird.mycontacts.entities.Users;
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
import java.util.HashMap;
import java.util.Map;

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

    public ResponseEntity<Map<String, Object>> register(String phone, String password) {
        final Map<String, Object> result = new HashMap<>();
        if (phone.length() != 13 && password.length() < 8){
            result.put("status", false);
            result.put("message", "Telefon raqami 13ta belgidan iborat bo'lishi kerak va parol kamida 6ta belgidan iborat bo'lishi kerak.");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        if (phone.length() != 13) {
            result.put("status", false);
            result.put("message", "Telefon raqami 13ta belgidan iborat bo'lishi kerak.");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        if (password.length() < 8) {
            result.put("status", false);
            result.put("message", "Parol kamida 6ta belgidan iborat bo'lishi kerak.");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        if (phone.matches("[0-9]+")) {
            result.put("status", false);
            result.put("message", "Telefon raqamida faqat '+' va raqamlar ishtirok etishi shart.");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        if (usersRepository.existsByPhone(phone)){
            result.put("status", false);
            result.put("message", "Bu foydalanuvchi allaqachon ro'yhatdan o'tgan.");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        final Users users = new Users();
        users.setPhone(phone);
        users.setPassword(passwordEncoder.encode(password));
        final Roles roles = rolesRepository.findByName("USER").get();
        users.setRoles(Collections.singleton(roles));

        usersRepository.save(users);

        result.put("status", false);
        result.put("message", "Foydalanuvchi ro'yhatdan o'tdi.");
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Map<String, Object>> login(String phone, String password) {
        final Map<String, Object> result = new HashMap<>();
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenGenerator.generateToken(authentication);
        result.put("status", true);
        result.put("message", "Foydalanuchi tizimga kirdi.");
        result.put("token-type", "Bearer");
        result.put("token", token);
        return ResponseEntity.ok(result);
    }
}
