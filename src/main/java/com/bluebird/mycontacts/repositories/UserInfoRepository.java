package com.bluebird.mycontacts.repositories;

import com.bluebird.mycontacts.entities.UserInfo;
import com.bluebird.mycontacts.models.AvailableContactResult;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByPhone(String phone);

    @Query(value = "select T2.contact_number, UI.first_name, UI.last_name, UI.picture, UI.bio from (select * from (select id as uid from user_info where phone = :phone) as T1 inner join users_contacts as UC on T1.uid = UC.user_id) as T2 inner join user_info as UI on T2.contact_number = UI.phone limit 20;", nativeQuery = true)
    List<List<Object>> availableContacts(@Param("phone") String phone);

    @Modifying
    @Transactional
    @Query(value = "delete from post_likes where user_info_id = :userId and post_id = :postId", nativeQuery = true)
    void deleteLike(@Param("userId") Long userId, @Param("postId") Long postId);

    @Modifying
    @Transactional
    @Query(value = "insert into post_likes (user_info_id, post_id) VALUES (:userId, :postId)", nativeQuery = true)
    void insertLike(@Param("userId") Long userId, @Param("postId") Long postId);

    @Query(value = "select * from post_likes where post_id = :postId and user_info_id = :userId", nativeQuery = true)
    List<Object> check(@Param("userId") Long userId, @Param("postId") Long postId);
}
