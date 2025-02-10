package com.example.BlogApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class UserService {

    @Autowired
    JWTService jwtservice;

    @Autowired
    private UserRepo repo;

    @Autowired
    AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        user.setBlogs(new ArrayList<>());
        return repo.save(user);
    }

    public String verify(Users user) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (authentication.isAuthenticated()) {
            Users authenticatedUser = repo.findByUsername(user.getUsername());
            if (authenticatedUser != null && authenticatedUser.getRoles() != null) {
                return jwtservice.generateToken(authenticatedUser.getUsername(), authenticatedUser.getRoles());
            }
        }
        return "failure";
    }



}
