package com.springboot.testing.dto.post;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCreateDto {
    private String title;
    private String description;

}
