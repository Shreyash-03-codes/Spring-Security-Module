package com.security.learn.controller;

import com.security.learn.dto.posts.PostDto;
import com.security.learn.dto.response.ApiResponse;
import com.security.learn.service.posts.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostsController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostDto>>> getPosts(){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(postService.getAllPosts()));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PostDto>> createPost(@RequestBody PostDto postDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(postService.createPost(postDto)));
    }
}
