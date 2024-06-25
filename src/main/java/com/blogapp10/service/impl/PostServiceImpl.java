package com.blogapp10.service.impl;

import com.blogapp10.entity.Post;
import com.blogapp10.exception.ResourceNotFound;
import com.blogapp10.payload.ListPostDto;
import com.blogapp10.payload.PostDto;
import com.blogapp10.repository.PostRepository;
import com.blogapp10.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);
        return mapToDto(savedPost);
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);

    }

    @Override
    public boolean postExists(long id) {
        return postRepository.findById(id).isPresent();
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFound("Post not found with id: "+ id)
        );
        return mapToDto(post);
    }

    @Override
    public ListPostDto getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        //List<Post> posts = postRepository.findAll();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> all = postRepository.findAll(pageable);
        List<Post> posts = all.getContent();
        List<PostDto> postDto = posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        ListPostDto listPostDto = new ListPostDto();
        listPostDto.setPostDtos(postDto);
        listPostDto.setTotalPages(all.getTotalPages());
        listPostDto.setTotalElements(all.getTotalElements());
        listPostDto.setFirstPage(all.isFirst());
        listPostDto.setLastPage(all.isLast());
        listPostDto.setPageNumber(all.getNumber());
        return listPostDto;
    }

    Post mapToEntity(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }

    PostDto mapToDto(Post post) {
        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;
    }

}
