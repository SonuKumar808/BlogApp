package com.blogapp10.service;

import com.blogapp10.payload.CommentDto;
import com.blogapp10.payload.PostDto;
import com.blogapp10.payload.PostWithCommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, long postId);
    public PostWithCommentDto getAllCommentsByPostId(long id);

    public CommentDto getCommentById(long id);
}
