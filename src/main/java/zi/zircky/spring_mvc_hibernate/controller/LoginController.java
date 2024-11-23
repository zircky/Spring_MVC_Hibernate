package zi.zircky.spring_mvc_hibernate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zi.zircky.spring_mvc_hibernate.model.User;
import zi.zircky.spring_mvc_hibernate.service.RoleService;
import zi.zircky.spring_mvc_hibernate.service.user.UserService;

import java.util.List;

@Controller
public class LoginController {
  private final UserService userService;
  private final RoleService roleService;

  public LoginController(UserService service, RoleService roleService) {
    this.userService = service;
    this.roleService = roleService;
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/sign-up")
  public String createForm(Model model) {
    model.addAttribute("user", new User());
    model.addAttribute("roles", roleService.getAllRoles());
    return "sign-up";
  }

  @PostMapping("/sign-up")
  public String create(@ModelAttribute("user") User user,
                       BindingResult bindingResult,
                       @RequestParam("roles") List<Long> roleIds) {
    if (bindingResult.hasErrors()) {
      return "sign-up";
    }
    user.setRoles(roleService.findByIds(roleIds));
    userService.createUser(user);
    return "redirect:/dashboard";
  }

}