package zi.zircky.spring_mvc_hibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import zi.zircky.spring_mvc_hibernate.service.RoleService;
import zi.zircky.spring_mvc_hibernate.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

  private final UserService userService;
  private final RoleService roleService;

  @Autowired
  public UserController(UserService userService, RoleService roleService) {
    this.userService = userService;
    this.roleService = roleService;
  }

  @GetMapping("/")
  public String index() {
    return "home";
  }

  @GetMapping("/user")
  public String readUser(Principal principal, Model model) {
    model.addAttribute("user", userService.findByUsername(principal.getName()));
    return "users";
  }
}
