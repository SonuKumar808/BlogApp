package com.blogapp10.payload;

import lombok.Data;

@Data
public class Signup {

    private String name;
    private String email;
    private String username;
    private String password;
}
