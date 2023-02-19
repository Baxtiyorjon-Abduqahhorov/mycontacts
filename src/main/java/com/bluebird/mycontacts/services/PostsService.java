package com.bluebird.mycontacts.services;

import com.bluebird.mycontacts.entities.Posts;
import com.bluebird.mycontacts.entities.UserInfo;
import com.bluebird.mycontacts.extra.AppVariables;
import com.bluebird.mycontacts.models.AvailableContactResult;
import com.bluebird.mycontacts.models.PostsResultModel;
import com.bluebird.mycontacts.models.post.*;
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
import java.util.*;

@Service
public class PostsService {
    private final PostsRepository postsRepository;

    private final TokenGenerator tokenGenerator;

    private final UserInfoRepository userInfoRepository;

    private final FileService fileService;


    private final FindService findService;

    public PostsService(PostsRepository postsRepository, TokenGenerator tokenGenerator, UserInfoRepository userInfoRepository, FileService fileService, FindService findService) {
        this.postsRepository = postsRepository;
        this.tokenGenerator = tokenGenerator;
        this.userInfoRepository = userInfoRepository;
        this.fileService = fileService;
        this.findService = findService;
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
        posts.setPicture(file == null ? null : fileService.getFileName(fileService.saveFile(file)));
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

    public List<Posts> getLastPost(HttpServletRequest request) {
        final String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        final UserInfo userInfo = userInfoRepository.findByPhone(phone).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        final List<Posts> list = new ArrayList<>();
        for (Object item : postsRepository.lastPosts(phone)) {
            final Posts post = postsRepository.findById(Long.valueOf(String.valueOf(item))).orElse(null);
            list.add(post);
        }
        return list;
    }

    public Map<String, Object> getLike(HttpServletRequest request, Long postId) {
        final String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        final UserInfo userInfo = userInfoRepository.findByPhone(phone).orElse(null);
        final Posts post = postsRepository.findById(postId).orElse(null);
        final Map<String, Object> result = new HashMap<>();
        if (userInfo == null || post == null) {
            throw new UsernameNotFoundException("User or Post not found");
        }
        result.put("count", Long.valueOf(postsRepository.countLikes(postId).get(0).get(0).toString()));
        if (userInfoRepository.check(userInfo.getId(), post.getId()).size() == 0) {
            result.put("like", false);
            return result;
        }
        result.put("like", true);
        return result;
    }

    public ResponseEntity<Boolean> setLike(HttpServletRequest request, Long postId) {
        final String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        final UserInfo userInfo = userInfoRepository.findByPhone(phone).orElse(null);
        final Posts post = postsRepository.findById(postId).orElse(null);
        if (userInfo == null || post == null) {
            throw new UsernameNotFoundException("User or Post not found");
        }
        if (userInfoRepository.check(userInfo.getId(), post.getId()).size() == 0) {
            userInfoRepository.insertLike(userInfo.getId(), post.getId());
            return ResponseEntity.ok(true);
        }
        userInfoRepository.deleteLike(userInfo.getId(), post.getId());
        return ResponseEntity.ok(false);
    }

    public ResponseEntity<PostsResultModel> findById(Long postId) {
        final Posts posts = postsRepository.findById(postId).orElse(null);
        final PostsResultModel postsResultModel = new PostsResultModel(posts.getId(), posts.getPicture(), posts.getCaption(), posts.getCreatedDate(), posts.getEditedDate(), posts.getUserInfo());
        return ResponseEntity.ok(postsResultModel);
    }

    public ResponseEntity<PostResultModel> full(HttpServletRequest request) {
        final String phone = tokenGenerator.getUsernameFromToken(tokenGenerator.getTokenFromRequest(request));
        final UserInfo userInfo = userInfoRepository.findByPhone(phone).orElse(null);
        final List<UserModel> users = new ArrayList<>();
        final List<UserLikeModel> userLikeModel = new ArrayList<>();
        final List<PostModel> posts = new ArrayList<>();
        final Map<String, List<String>> last4Posts = new HashMap<>();

        long userID = 0;
        for (Posts e : getLastPost(request)) {
            if (userID != e.getUserInfo().getId()) {
                List<String> picList = new ArrayList<>();
                for (List<Object> i : postsRepository.last4(e.getUserInfo().getId())) {
                    picList.add(i.get(4).toString());

                }
                last4Posts.put(String.valueOf(e.getUserInfo().getId()), picList.size() == 4 ? picList : null);
            }
            userID = e.getUserInfo().getId();

        }

        for (AvailableContactResult e : Objects.requireNonNull(findService.checker(request).getBody())) {
            final UserModel userModel = new UserModel(e.getUser_id(), e.getPicture(), e.getFirst_name());
            userLikeModel.add(new UserLikeModel(e.getUser_id(), e.getFirst_name(), e.getPicture(), e.getBio()));
            users.add(userModel);
        }

        long uId = 0;
        for (Posts e : Objects.requireNonNull(getLastPost(request))) {
            final PostModel postModel = new PostModel(e.getId(), AppVariables.IMAGE_SERVER_URL + e.getPicture(), e.getCaption(), getUserModel(userLikeModel, e.getUserInfo()),
                    uId != e.getUserInfo().getId() ? last4Posts.get(String.valueOf(e.getUserInfo().getId())) : null,
                    AppVariables.SERVER_URL + "/api/posts/comments", (boolean) getLike(request, e.getId()).get("like"));
            posts.add(postModel);
            uId = e.getUserInfo().getId();
        }

        return ResponseEntity.ok(new PostResultModel(userInfo.getId(), new PostDataModel(users, posts)));
    }

    public UserLikeModel getUserModel(List<UserLikeModel> list, UserInfo userInfo) {
        for (UserLikeModel u : list) {
            if (u.getId().equals(userInfo.getId())) {
                return u;
            }
        }
        return null;
    }

    public ResponseEntity<List<String>> comments() {
        final List<String> comments = new ArrayList<>();
        comments.add("Zo'r ekan");
        comments.add("Norm");
        comments.add("Salomat");
        return ResponseEntity.ok(comments);
    }

}
