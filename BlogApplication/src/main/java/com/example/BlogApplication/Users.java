package com.example.BlogApplication;

import com.example.BlogApplication.Blog;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class Users {

    @Id
    private ObjectId id;
    private String email;
    private String username;
    private String password;
    private List<String> roles;
    private String address;




    @DBRef
    private List<Blog> blogs =new ArrayList<>();

    public Users() {
    }

    public Users(String username, String password, String address,  String email, List<String> roles, List<Blog> blogs) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.blogs = blogs;
        this.email = email;
        this.address = address;

    }

    // Getters and Setters
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    public List<Blog> getBlogs() { return blogs; }
    public void setBlogs(List<Blog> blogs) { this.blogs = blogs; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }



    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }


}
