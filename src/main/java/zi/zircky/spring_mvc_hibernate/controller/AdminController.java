package zi.zircky.spring_mvc_hibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zi.zircky.spring_mvc_hibernate.model.User;
import zi.zircky.spring_mvc_hibernate.service.RoleService;
import zi.zircky.spring_mvc_hibernate.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
  private final UserService userService;
  private final RoleService roleService;

  @Autowired
  public AdminController(UserService userService, RoleService roleService) {
    this.userService=userService;
    this.roleService=roleService;
  }

  @GetMapping()
  public String readAllUsers(Model model) {
    model.addAttribute("users", userService.getAllUser());
    return "admin";
  }

  @GetMapping("/login")
  public String loginForm(Model model) {
    model.addAttribute("user", new User());
    model.addAttribute("roles", roleService.getAllRoles());
    return "logins";
  }

  @PostMapping("/login")
  public String login(@ModelAttribute("user") User user,
                       BindingResult bindingResult,
                       @RequestParam("role") String selectedRole) {
    if (bindingResult.hasErrors()) {
      return "logins";
    }
    if (selectedRole.equals("ROLE_USER")) {
      user.setRoles(roleService.findByName("ROLE_USER"));
    } else if (selectedRole.equals("ROLE_ADMIN")) {
      user.setRoles(roleService.getAllRoles());
    }
    userService.createUser(user);
    return "redirect:/admin";
  }

  @GetMapping("/add")
  public String createForm(Model model) {
    model.addAttribute("user", new User());
    model.addAttribute("roles", roleService.getAllRoles());
    return "add";
  }

  @PostMapping("/addauser")
  public String create(@ModelAttribute("user") User user,
                       BindingResult bindingResult,
                       @RequestParam("role") String selectedRole) {
    if (bindingResult.hasErrors()) {
      return "add";
    }
    if (selectedRole.equals("ROLE_USER")) {
      user.setRoles(roleService.findByName("ROLE_USER"));
    } else if (selectedRole.equals("ROLE_ADMIN")) {
      user.setRoles(roleService.getAllRoles());
    }
    userService.createUser(user);
    return "redirect:/admin";
  }

  @GetMapping("/update")
  public String updateForm(Model model,
                           @RequestParam("id") Long id) {
    model.addAttribute(userService.readUserById(id));
    return "edit";
  }

  @PostMapping("/updateauser")
  public String update(@ModelAttribute("user") User user,
                       BindingResult bindingResult,
                       @RequestParam("role") String selectedRole,
                       @RequestParam("id") Long id) {
    if (bindingResult.hasErrors()) {
      return "edit";
    }
    if (selectedRole.equals("ROLE_USER")) {
      user.setRoles(roleService.findByName("ROLE_USER"));
    } else if (selectedRole.equals("ROLE_ADMIN")) {
      user.setRoles(roleService.getAllRoles());
    }
    userService.updateUser(id, user);
    return "redirect:/admin";
  }

  @PostMapping("/delete")
  public String delete(@RequestParam("id") Long id) {
    userService.delete(id);
    return "redirect:/admin";
  }
}
