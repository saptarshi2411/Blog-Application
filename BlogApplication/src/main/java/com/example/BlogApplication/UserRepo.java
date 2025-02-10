package com.example.BlogApplication;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepo extends MongoRepository<Users, String> {
    Users findByUsername(String username);
    List<Users> findByBlogsContaining(Blog blog);
}
