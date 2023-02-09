package com.bluebird.mycontacts.repositories;

import com.bluebird.mycontacts.entities.Posts;
import com.bluebird.mycontacts.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {


    Optional<List<Posts>> findByUserInfo(UserInfo userInfo);

    @Query(value = "select P.id as post_id from (select T2.contact_number, UI.first_name, UI.last_name, UI.picture, UI.bio, UI.id from (select * from (select id as uid from user_info where phone = :phone) as T1 inner join users_contacts as UC on T1.uid = UC.user_id) as T2 inner join user_info as UI on T2.contact_number = UI.phone limit 20) as T3 inner join posts as P on T3.id = P.user_id where P.created_date >= now() - interval 24 hour", nativeQuery = true)
    List<Object> lastPosts(@Param("phone") String phone);

    @Query(value = "select count(post_id) from post_likes where post_id = :postId", nativeQuery = true)
    List<List<Object>> countLikes(@Param("postId") Long postId);

    @Query(value = "SELECT * FROM posts where user_id = :postUserId and id != :postId and created_date < now() - interval 24 hour order by created_date desc limit 4", nativeQuery = true)
    List<List<Object>> last4(@Param("postUserId") Long postUserId, @Param("postId") Long postId);
}