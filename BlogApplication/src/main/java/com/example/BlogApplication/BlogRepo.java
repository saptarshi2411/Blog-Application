package com.example.BlogApplication;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepo extends MongoRepository<Blog, String> {
    Optional<Blog> findByRandomId(int randomId);
    void deleteByRandomId(int randomId);
}
