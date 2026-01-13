package com.springboot.testing.service.post;

import com.springboot.testing.dto.post.PostCreateDto;
import com.springboot.testing.dto.post.PostResponseDto;
import com.springboot.testing.dto.response.ApiResponse;

public interface PostService {
    ApiResponse<PostResponseDto> createPost(PostCreateDto postCreateDto);

    ApiResponse<PostResponseDto> getPost(Long id);

    ApiResponse<String> deletePost(Long id);
}
