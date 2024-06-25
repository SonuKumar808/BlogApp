package com.blogapp10.service;

import com.blogapp10.payload.ListPostDto;
import com.blogapp10.payload.PostDto;

public interface PostService {
    public PostDto createPost(PostDto postDto);

    void deletePost(long id);
    public boolean postExists(long id);

    PostDto getPostById(long id);

    ListPostDto getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
}
