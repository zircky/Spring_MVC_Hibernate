package zi.zircky.spring_mvc_hibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zi.zircky.spring_mvc_hibernate.model.User;
import zi.zircky.spring_mvc_hibernate.service.UserService;

import java.util.List;

@Controller
public class UserController {

  private final UserService userService;
  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(value = "/")
  public String listOfUsers(Model model) {
    List<User> users = userService.getAllUser();
    model.addAttribute("users", users);
    return "users";
  }

  @GetMapping(value = "/add")
  public String addUser(Model model) {
    model.addAttribute("user", new User());
    return "add";
  }

  @GetMapping("/edit")
  public String editUser(@RequestParam("id") Long id, Model model) {
    User user = userService.findById(id);
    model.addAttribute("user", user);
    return "edit";
  }

  @PostMapping(value = "/add")
  public String saveUser(@ModelAttribute User user, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "add";
    }
    userService.save(user);
    return "redirect:/";
  }

  @PostMapping(value = "/edit")
  public String updateUser(@RequestParam(name = "id") Long id, @ModelAttribute User user, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "edit";
    }
    userService.save(user);
    return "redirect:/";
  }

  @GetMapping(value = "/delete")
  public String deleteUser(@RequestParam("id") Long id) {
    userService.delete(id);
    return "redirect:/";
  }
}
