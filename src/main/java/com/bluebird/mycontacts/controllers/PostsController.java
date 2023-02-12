package com.bluebird.mycontacts.controllers;

import com.bluebird.mycontacts.entities.Posts;
import com.bluebird.mycontacts.models.PostsResultModel;
import com.bluebird.mycontacts.models.post.PostResultModel;
import com.bluebird.mycontacts.repositories.PostsRepository;
import com.bluebird.mycontacts.services.PostsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostsService postsService;

    public PostsController(PostsService postsService,
                           PostsRepository postsRepository) {
        this.postsService = postsService;
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> create(@RequestParam("caption") String caption, @RequestParam(value = "picture", required = false) MultipartFile file, HttpServletRequest request) throws IOException {
        return postsService.create(caption, file, request);
    }

    @PostMapping("/edit")
    public ResponseEntity<Boolean> edit(@RequestParam("caption") String caption, @RequestParam(value = "picture", required = false) MultipartFile file, @RequestParam("id") Long id) throws IOException {
        return postsService.edit(id, caption, file);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestParam("id") Long id) {
        return postsService.delete(id);
    }


    @GetMapping("/one/{postId}")
    public ResponseEntity<PostsResultModel> getPost(@PathVariable Long postId) {
        return postsService.findById(postId);
    }

    @PostMapping("/like")
    public ResponseEntity<Boolean> setLike(@RequestParam("postId") Long postId, HttpServletRequest request) {
        return postsService.setLike(request, postId);
    }

    @GetMapping("/full")
    public ResponseEntity<PostResultModel> full(HttpServletRequest request) {
        return postsService.full(request);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<String>> comments(){
        return postsService.comments();
    }

}
