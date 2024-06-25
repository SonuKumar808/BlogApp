package com.blogapp10.service.impl;

import com.blogapp10.entity.Comment;
import com.blogapp10.entity.Post;
import com.blogapp10.exception.ResourceNotFound;
import com.blogapp10.payload.CommentDto;
import com.blogapp10.payload.PostDto;
import com.blogapp10.payload.PostWithCommentDto;
import com.blogapp10.repository.CommentRepository;
import com.blogapp10.repository.PostRepository;
import com.blogapp10.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private ModelMapper modelMapper;
    private PostRepository postRepository;

    @Override
    public CommentDto createComment(CommentDto commentDto, long postId) {
        Optional<Post> byId = postRepository.findById(postId);
        Post post = byId.get();

        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        CommentDto dto = mapToDto(savedComment);
        return dto;
    }

    public PostWithCommentDto getAllCommentsByPostId(long id) {
        Post post = postRepository.findById(id).get();

        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());

        List<Comment> comments = commentRepository.findByPostId(id);
        List<CommentDto> dtos = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        PostWithCommentDto postWithCommentDto = new PostWithCommentDto();

        postWithCommentDto.setCommentDto(dtos);
        postWithCommentDto.setPostDto(dto);
        return postWithCommentDto;
    }

    @Override
    public CommentDto getCommentById(long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Comment not found with id: " + id)
        );
        return mapToDto(comment);
    }

    Comment mapToEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }

    CommentDto mapToDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

}

