package com.security.learn.service.posts;

import com.security.learn.dto.posts.PostDto;
import com.security.learn.dto.response.ApiResponse;

import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts();

    PostDto createPost(PostDto postDto);
}
