package com.springboot.testing.service;


import com.springboot.testing.dto.post.PostCreateDto;
import com.springboot.testing.dto.post.PostResponseDto;
import com.springboot.testing.dto.response.ApiResponse;
import com.springboot.testing.entity.Post;
import com.springboot.testing.repository.PostRepository;
import com.springboot.testing.service.post.PostServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import org.modelmapper.ModelMapper;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class PostServiceImplTests {

    @Spy
    private  ModelMapper modelMapper;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private PostCreateDto postCreateDto;

    private ApiResponse<PostResponseDto> mockResponse;

    @Captor
    private ArgumentCaptor<Post> captor;

    @BeforeEach
    public void setUp(){
        postCreateDto=PostCreateDto.builder()
                .title("happy diwali")
                .description("happy diwali to all , diwali is our india`s biggest festival")
                .build();

        PostResponseDto postResponseDto=PostResponseDto.builder()
                .title("happy diwali")
                .description("happy diwali to all , diwali is our india`s biggest festival")
                .createdBy("SYSTEM")
                .updatedBy("SYSTEM")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();


        mockResponse=new ApiResponse<>(postResponseDto);
    }

    @Test
    public void testCreatePost_WhenPostCreated_ThenReturnApiResponseContainingThePostResponseDto(){


        // arrange

        Post mockEntity=modelMapper.map(postCreateDto,Post.class);
        Mockito.when(postRepository.save(any())).thenReturn(mockEntity);



        //act

        ApiResponse<PostResponseDto> apiResponse=postService.createPost(postCreateDto);
        PostResponseDto postResponseDto=apiResponse.getData();

        //verify

        verify(postRepository,times(1)).save(captor.capture());

        Post mockedPost=captor.getValue();


        //assert

        Assertions.assertThat(mockedPost).isNotNull();

        Assertions.assertThat(apiResponse).isNotNull();
        Assertions.assertThat(apiResponse).isInstanceOf(ApiResponse.class);
        Assertions.assertThat(postResponseDto).isNotNull();
        Assertions.assertThat(postResponseDto).isInstanceOf(PostResponseDto.class);
//        Assertions.assertThat(capturedApiResponse).isEqualTo(mockResponse);
//        Assertions.assertThat(postResponseDto).isEqualTo(capturedPostDto);

    }


    @Test
    public void testGetPost_WhenIdPassed_ThenReturnApiResponseContainingPostResponse(){
        //arrange
        Post mockEntity=modelMapper.map(postCreateDto,Post.class);
        when(postRepository.findById(any())).thenReturn(Optional.of(mockEntity));


        //act
        ApiResponse<PostResponseDto> apiResponse=postService.getPost(1L);
        PostResponseDto postResponseDto=apiResponse.getData();

        //verify
        verify(postRepository,times(1)).findById(1L);


        //assert

        Assertions.assertThat(apiResponse).isNotNull();
        Assertions.assertThat(apiResponse).isInstanceOf(ApiResponse.class);

        Assertions.assertThat(postResponseDto).isNotNull();
        Assertions.assertThat(postResponseDto).isInstanceOf(PostResponseDto.class);


    }


}
