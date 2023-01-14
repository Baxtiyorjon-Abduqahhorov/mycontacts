package com.bluebird.mycontacts.repositories;

import com.bluebird.mycontacts.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByPhone(String username);

    boolean existsByPhone(String username);
}