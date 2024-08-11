package zi.zircky.spring_mvc_hibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zi.zircky.spring_mvc_hibernate.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

  private final UserService userService;
  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping()
  public String index() {
    return "index";
  }

  @GetMapping("/user")
  public String readUser(Principal principal, Model model) {
    model.addAttribute("user", userService.findByUsername(principal.getName()));
    return "users";
  }
}
