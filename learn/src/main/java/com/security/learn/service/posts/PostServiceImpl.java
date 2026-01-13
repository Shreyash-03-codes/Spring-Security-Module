package com.security.learn.service.posts;

import com.security.learn.dto.posts.PostDto;
import com.security.learn.dto.response.ApiResponse;
import com.security.learn.entity.Post;
import com.security.learn.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(p->
                     modelMapper.map(p,PostDto.class)
                ).toList();
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post postToSave=modelMapper.map(postDto,Post.class);
        Post saved=postRepository.save(postToSave);
        return modelMapper.map(saved,PostDto.class);
    }
}
