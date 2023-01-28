package com.bluebird.mycontacts.repositories;

import com.bluebird.mycontacts.entities.UserInfo;
import com.bluebird.mycontacts.models.AvailableContactResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByPhone(String phone);

    @Query(value = "select T2.contact_number, UI.first_name, UI.last_name, UI.picture, UI.bio from (select * from (select id as uid from user_info where phone = :phone) as T1 inner join users_contacts as UC on T1.uid = UC.user_id) as T2 inner join user_info as UI on T2.contact_number = UI.phone limit 20;", nativeQuery = true)
    List<List<Object>> availableContacts(@Param("phone") String phone);
}
