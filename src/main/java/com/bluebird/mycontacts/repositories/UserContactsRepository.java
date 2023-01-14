package com.bluebird.mycontacts.repositories;

import com.bluebird.mycontacts.entities.UsersContacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactsRepository extends JpaRepository<UsersContacts,Long> {

}
