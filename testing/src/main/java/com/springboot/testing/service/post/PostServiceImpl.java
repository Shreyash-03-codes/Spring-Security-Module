package com.springboot.testing.service.post;

import com.springboot.testing.dto.post.PostCreateDto;
import com.springboot.testing.dto.post.PostResponseDto;
import com.springboot.testing.dto.response.ApiResponse;
import com.springboot.testing.entity.Post;
import com.springboot.testing.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{


    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    @Override
    public ApiResponse<PostResponseDto> createPost(PostCreateDto postCreateDto) {
        if(postRepository.findByTitle(postCreateDto.getTitle()).isPresent()){
            throw new IllegalStateException("post title alredy exists");
        }
        Post post=modelMapper.map(postCreateDto,Post.class);
        Post savedPost=postRepository.save(post);
        PostResponseDto postResponseDto=modelMapper.map(savedPost, PostResponseDto.class);
        return new ApiResponse<>(postResponseDto);
    }

    @Override
    public ApiResponse<PostResponseDto> getPost(Long id) {
        Post post=postRepository.findById(id).orElseThrow(()->
                new IllegalStateException("post with "+id+" No found")
        );
        PostResponseDto postResponseDto=modelMapper.map(post, PostResponseDto.class);
        return new ApiResponse<>(postResponseDto);
    }

    public ApiResponse<String> deletePost(Long id){
        Post post=postRepository.findById(id).orElseThrow(()->
                new RuntimeException("User with "+id+" Not found")
        );
        postRepository.delete(post);
        return new ApiResponse<>("User deleted");
    }
}
