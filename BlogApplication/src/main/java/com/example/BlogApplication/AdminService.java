package com.example.BlogApplication;

import com.example.BlogApplication.UserRepo;
import com.example.BlogApplication.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class AdminService {


    @Autowired
    UserRepo repo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveAdmin(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        repo.save(user);
    }

    public List<Users> getAll() {
        return repo.findAll();
    }
}
