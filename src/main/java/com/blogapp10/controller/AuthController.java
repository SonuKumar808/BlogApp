package com.blogapp10.controller;

import com.blogapp10.entity.User;
import com.blogapp10.payload.LoginDto;
import com.blogapp10.payload.Signup;
import com.blogapp10.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/sign-up")
    public ResponseEntity<String> createUser(@RequestBody Signup signup) {
        if (userRepository.existsByEmail(signup.getEmail())) {
            return new ResponseEntity<>("email is already registered", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (userRepository.existsByUsername(signup.getUsername())) {
            return new ResponseEntity<>("username is already registered!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user = new User();
        user.setName(signup.getName());
        user.setEmail(signup.getEmail());
        user.setUsername(signup.getUsername());
        user.setPassword(passwordEncoder.encode(signup.getPassword()));

        userRepository.save(user);
        return new ResponseEntity<>("user registered", HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> userSignIn(@RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken u = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication a = authenticationManager.authenticate(u);

        SecurityContextHolder.getContext().setAuthentication(a);
        return new ResponseEntity<>("Sign-in Successful", HttpStatus.OK);
    }
}
