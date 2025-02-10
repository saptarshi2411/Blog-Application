package com.example.BlogApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BlogService {

    @Autowired
    private BlogRepo repo;

    @Autowired
    private UserRepo userRepo;

    public List<Blog> getBlogs() {
        return repo.findAll();
    }

    public Blog getBlogById(int randomId) {
        return repo.findByRandomId(randomId).orElse(null);
    }



    public void updateBlog(Blog blog, int randomId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();


        Blog existingBlog = repo.findByRandomId(randomId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        Users user = userRepo.findByUsername(username);
        if (user != null && user.getBlogs().stream().anyMatch(b -> b.getRandomId() == randomId)) {

            existingBlog.setContent(blog.getContent());
            existingBlog.setCategory(blog.getCategory());
            existingBlog.setTitle(blog.getTitle());

            repo.save(existingBlog);
        } else {
            throw new RuntimeException("You are not authorized to update this blog");
        }
    }


    public List<Blog> getOtherUsersBlogsHome() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users user = userRepo.findByUsername(username);
        Set<String> userBlogIds = user.getBlogs().stream()
                .map(Blog::getProdId)
                .collect(Collectors.toSet());

        List<Blog> allBlogs = repo.findAll();
        return allBlogs.stream()
                .filter(blog -> !userBlogIds.contains(blog.getProdId()))
                .collect(Collectors.toList());
    }

    public List<Blog> getUserOwnBlogs() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users user = userRepo.findByUsername(username);
        return user.getBlogs();
    }

    public List<Blog> getCategoryBlogs(String category) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users user = userRepo.findByUsername(username);
        Set<String> userBlogIds = user.getBlogs().stream()
                .map(Blog::getProdId)
                .collect(Collectors.toSet());

        List<Blog> categoryBlogs = repo.findByCategory(category);
        return categoryBlogs.stream()
                .filter(blog -> !userBlogIds.contains(blog.getProdId()))
                .collect(Collectors.toList());
    }

    public void deleteBlog(int randomId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();


        Blog blog = repo.findByRandomId(randomId).orElseThrow(() -> new RuntimeException("Blog not found"));


        Users user = userRepo.findByUsername(username);
        if (user != null && user.getBlogs().stream().anyMatch(b -> b.getRandomId()==(randomId))) {

            List<Users> usersWithBlog = userRepo.findByBlogsContaining(blog);

            for (Users userWithBlog : usersWithBlog) {

                userWithBlog.getBlogs().removeIf(b -> b.getRandomId()==(blog.getRandomId()));
                userRepo.save(userWithBlog);
            }


            repo.deleteByRandomId(randomId);
        } else {
            throw new RuntimeException("You are not authorized to delete this blog");
        }
    }


    public void addBlog(Blog blog, String username) {
        Blog savedBlog = repo.save(blog);

        Users user = userRepo.findByUsername(username);
        if (user != null) {
            user.getBlogs().add(savedBlog);
            userRepo.save(user);
        }
    }

}
