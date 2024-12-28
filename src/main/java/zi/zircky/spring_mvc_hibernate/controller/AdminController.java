package zi.zircky.spring_mvc_hibernate.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zi.zircky.spring_mvc_hibernate.model.User;
import zi.zircky.spring_mvc_hibernate.service.RoleService;
import zi.zircky.spring_mvc_hibernate.service.user.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
  private final UserService userService;
  private final RoleService roleService;

  @Autowired
  public AdminController(UserService userService, RoleService roleService) {
    this.userService = userService;
    this.roleService = roleService;
  }

  @GetMapping()
  public String readAllUsers(Model model) {
    model.addAttribute("users", userService.getAllUser());
    return "admin";
  }

  @GetMapping("/create")
  public String createForm(Model model) {
    model.addAttribute("user", new User());
    model.addAttribute("roles", roleService.getAllRoles());
    return "create";
  }

  @PostMapping("/create")
  public String create(@ModelAttribute("user") User user,
                       BindingResult bindingResult,
                       @RequestParam("roles") List<Long> roleIds) {
    if (bindingResult.hasErrors()) {
      return "create";
    }
    user.setRoles(roleService.findByIds(roleIds));
    userService.createUser(user);
    return "redirect:/admin";
  }

  @GetMapping("/update")
  public String updateForm(Model model,
                           @RequestParam("id") Long id) {
    model.addAttribute("user", userService.readUserById(id));
    return "update";
  }

  @PostMapping("/update")
  public String update(@ModelAttribute("user") @Valid User user,
                       BindingResult bindingResult,
                       @RequestParam("roles") List<Long> roleIds,
                       @RequestParam("id") Long id) {
    if (bindingResult.hasErrors()) {
      return "update";
    }
    user.setRoles(roleService.findByIds(roleIds));
    userService.updateUser(id, user);
    return "redirect:/admin";
  }

  @PostMapping("/delete/{id}")
  @DeleteMapping("/delete/{id}")
  public String deleteUser(@PathVariable Long id) {
    userService.delete(id);
    return "redirect:/admin";
  }

}
