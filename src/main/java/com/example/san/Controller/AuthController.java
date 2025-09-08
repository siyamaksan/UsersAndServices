package com.example.san.Controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Login Controller
@Controller
@Slf4j
public class AuthController {

  @GetMapping("/login")
  public String loginPage(@RequestParam(value = "error", required = false) String error,
      @RequestParam(value = "logout", required = false) String logout,
      Model model) {

    if (error != null) {
      model.addAttribute("error", "Invalid username or password");
    }

    if (logout != null) {
      model.addAttribute("message", "You have been logged out successfully");
    }

    return "login";
  }

  @GetMapping("/loginFailed")
  public String loginFailed(Model model) {
    model.addAttribute("error", "Login failed. Please try again.");
    return "login";
  }

  @GetMapping("/access-denied")
  public String accessDenied() {
    return "access-denied";
  }
}