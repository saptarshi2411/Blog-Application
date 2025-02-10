package com.example.BlogApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
   AdminService adminService;

    @Autowired
    JWTService jwtService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<Users> all = adminService.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createUser(@RequestBody Users user) {

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(List.of("ADMIN"));
        }
        adminService.saveAdmin(user);
        return ResponseEntity.ok(user);

    }

}

