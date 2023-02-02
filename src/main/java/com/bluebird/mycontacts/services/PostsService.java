package com.bluebird.mycontacts.services;

import com.bluebird.mycontacts.entities.Posts;
import com.bluebird.mycontacts.entities.UserInfo;
import com.bluebird.mycontacts.repositories.PostsRepository;
import com.bluebird.mycontacts.repositories.UserInfoRepository;
import com.bluebird.mycontacts.security.TokenGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostsService {
    private final PostsRepository postsRepository;

    private final TokenGenerator tokenGenerator;

    private final UserInfoRepository userInfoRepository;

    private final FileService fileService;

    public PostsService(PostsRepository postsRepository, TokenGenerator tokenGenerator, UserInfoRepository userInfoRepository, FileService fileService) {
        this.postsRepository = postsRepository;
        this.tokenGenerator = tokenGenerator;
        this.userInfoRepository = userInfoRepository;
        this.fileService = fileService;
    }

    public ResponseEntity<Boolean> create(String caption, MultipartFile file, HttpServletRequest request) throws IOException {
        final String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        final UserInfo user = userInfoRepository.findByPhone(phone).orElse(null);
        final Timestamp now = Timestamp.from(new Date().toInstant());
        if (user == null) {
            return ResponseEntity.ok(false);
        }
        final Posts posts = new Posts();
        posts.setCaption(caption);
        posts.setCreatedDate(now);
        posts.setEditedDate(now);
        posts.setPicture(file == null ? null : fileService.saveFile(file));
        posts.setUserInfo(user);
        postsRepository.save(posts);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<Boolean> edit(Long id, String caption, MultipartFile file) throws IOException {
        final Posts post = postsRepository.findById(id).orElse(null);
        final Timestamp now = Timestamp.from(new Date().toInstant());
        if (post == null) {
            return ResponseEntity.ok(false);
        }
        final Posts editedPost = new Posts();
        editedPost.setId(id);
        editedPost.setPicture(file != null ? fileService.saveFile(file) : post.getPicture());
        editedPost.setCaption(caption != null ? caption : post.getCaption());
        editedPost.setCreatedDate(post.getCreatedDate());
        editedPost.setEditedDate(now);
        editedPost.setUserInfo(editedPost.getUserInfo());
        postsRepository.save(editedPost);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<Boolean> delete(Long id) {
        final Posts post = postsRepository.findById(id).orElse(null);
        if (post == null) {
            return ResponseEntity.ok(false);
        }
        postsRepository.delete(post);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<List<Posts>> getLastPost(HttpServletRequest request) {
        final String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        final UserInfo userInfo = userInfoRepository.findByPhone(phone).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        final List<Posts> list = new ArrayList<>();
        for (Object item : postsRepository.lastPosts(phone)) {
            final Posts post = postsRepository.findById(Long.valueOf(String.valueOf(item))).orElse(null);
            list.add(post);
        }
        return ResponseEntity.ok(list);
    }

}
