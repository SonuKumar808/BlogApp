package com.blogapp10.payload;

import com.blogapp10.entity.Post;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostWithCommentDto {
    private PostDto postDto;
    private List<CommentDto> commentDto = new ArrayList<>();
}
