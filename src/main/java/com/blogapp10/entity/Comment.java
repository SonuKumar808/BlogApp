package com.blogapp10.entity;


import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String message;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
