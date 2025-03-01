package zi.zircky.spring_mvc_hibernate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/sign-up")
  public String createForm() {
    return "sign-up";
  }

  @GetMapping("/admin")
  public String admin() {
    return "admin";
  }

  @GetMapping("/user")
  public String user() {
    return "user";
  }
}
