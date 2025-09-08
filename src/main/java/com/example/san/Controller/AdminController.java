package com.example.san.Controller;

import com.example.san.Model.BaseModel.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Method-level security example
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

  @GetMapping("/users")
  @PreAuthorize("hasAuthority('USER_READ')")
  public ResponseEntity<List<User>> getAllUsers() {
    // Implementation
    return ResponseEntity.ok(new ArrayList<>());
  }

  @PostMapping("/users")
  @PreAuthorize("hasAuthority('USER_WRITE')")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    // Implementation
    return ResponseEntity.ok(user);
  }
}