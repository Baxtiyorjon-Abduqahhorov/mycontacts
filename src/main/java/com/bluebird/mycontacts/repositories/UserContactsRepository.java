package com.bluebird.mycontacts.repositories;

import com.bluebird.mycontacts.entities.UserInfo;
import com.bluebird.mycontacts.entities.UsersContacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserContactsRepository extends JpaRepository<UsersContacts, Long> {

    Optional<List<UsersContacts>> findByUserInfo(UserInfo userInfo);
}
