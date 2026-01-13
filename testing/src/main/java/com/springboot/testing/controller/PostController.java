package com.springboot.testing.controller;

import com.springboot.testing.dto.post.PostCreateDto;
import com.springboot.testing.dto.post.PostResponseDto;
import com.springboot.testing.dto.response.ApiResponse;
import com.springboot.testing.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PostResponseDto>> createPost(@RequestBody PostCreateDto postCreateDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postCreateDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> getPost(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
    }
}
