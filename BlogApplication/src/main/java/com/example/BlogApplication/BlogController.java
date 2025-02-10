package com.example.BlogApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class BlogController {

    @Autowired
    private BlogService service;

    @GetMapping("/allBlogs")
    public ResponseEntity<List<Blog>> getBlogs() {
        List<Blog> blogs = service.getBlogs();
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("/otherBlogs")
    public ResponseEntity<List<Blog>> getOtherBlogs() {
        List<Blog> otherBlogs = service.getOtherUsersBlogsHome();
        return new ResponseEntity<>(otherBlogs, HttpStatus.OK);
    }

    @GetMapping("/ownBlogs")
    public ResponseEntity<List<Blog>> ownBlogs() {
        List<Blog> ownBlogs = service.getUserOwnBlogs();
        return new ResponseEntity<>(ownBlogs, HttpStatus.OK);
    }

    @PostMapping(value = "/addBlog", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addBlog(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("category") String category,
            @RequestParam("image") MultipartFile image) {
        try {

            Blog blog = new Blog();
            blog.setTitle(title);
            blog.setContent(content);
            blog.setCategory(category);


            if (image != null && !image.isEmpty()) {
                blog.setImageData(image.getBytes());
            }


            int randomId = (int) (Math.random() * 1000000);
            blog.setRandomId(randomId);


            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();


            service.addBlog(blog, username);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/blogs/{randomId}")
    public ResponseEntity<Void> deleteBlog(@PathVariable int randomId) {
        try {
            service.deleteBlog(randomId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/blogs/{randomId}")
    public ResponseEntity<Void> updateBlog(@RequestBody Blog blog, @PathVariable int randomId) {
        service.updateBlog(blog, randomId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/categoryBlogs/{category}")
    public ResponseEntity<List<Blog>> categoryBlogs(@PathVariable String category) {
        List<Blog> categoryBlogs = service.getCategoryBlogs(category);
        return new ResponseEntity<>(categoryBlogs, HttpStatus.OK);
    }

    @GetMapping("/blogs/{randomId}")
    public ResponseEntity<Blog> getBlogById(@PathVariable int ramdomId) {
        Blog blog = service.getBlogById(ramdomId);
        return blog != null ? ResponseEntity.ok(blog) : ResponseEntity.notFound().build();
    }






}
