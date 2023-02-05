package com.bluebird.mycontacts.repositories;

import com.bluebird.mycontacts.entities.UserInfo;
import com.bluebird.mycontacts.entities.UsersContacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserContactsRepository extends JpaRepository<UsersContacts, Long> {

    Optional<List<UsersContacts>> findByUserInfo(UserInfo userInfo);

    @Transactional
    @Modifying
    @Query(value = "delete from users_contacts where user_id = :uid", nativeQuery = true)
    void delAllByUserId(@Param("uid") Long id);


    boolean existsByUserInfo(UserInfo userInfo);
}
