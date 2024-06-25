package com.blogapp10.payload;

import lombok.Data;

import java.util.List;
@Data
public class ListPostDto {

    public List<PostDto> postDtos;
    private int totalPages;
    private long totalElements;
    private boolean firstPage;
    private boolean lastPage;
    private int pageNumber;
}
