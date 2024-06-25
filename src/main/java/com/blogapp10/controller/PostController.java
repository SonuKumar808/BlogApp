package com.blogapp10.controller;

import com.blogapp10.payload.ListPostDto;
import com.blogapp10.payload.PostDto;
import com.blogapp10.service.PostService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    //http://localhost:8080/api/posts
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result) {
        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/posts/2
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        if (postService.postExists(id)){
            postService.deletePost(id);
            return new ResponseEntity<>("Post is deleted!!!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Post not found!!!", HttpStatus.NOT_FOUND);
        }

    }

    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=name
    @GetMapping
    public ResponseEntity <ListPostDto> fetchAllPosts(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        ListPostDto listPostDto = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(listPostDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity <PostDto> getPostById(@PathVariable long id) {
        PostDto postById = postService.getPostById(id);
        return new ResponseEntity<>(postById, HttpStatus.OK);
    }
}
