package com.bluebird.mycontacts.controllers;

import com.bluebird.mycontacts.entities.Posts;
import com.bluebird.mycontacts.services.PostsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostsService postsService;

    public PostsController(PostsService postsService) {
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

    @GetMapping("/last")
    public ResponseEntity<List<Posts>> last(HttpServletRequest request){
        return postsService.getLastPost(request);
    }

}
