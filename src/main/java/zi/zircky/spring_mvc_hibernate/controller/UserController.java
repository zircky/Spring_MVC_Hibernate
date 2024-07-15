package zi.zircky.spring_mvc_hibernate.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zi.zircky.spring_mvc_hibernate.model.User;
import zi.zircky.spring_mvc_hibernate.service.UserServiceImpl;

import java.util.List;

@Controller
public class UserController {

  private final UserServiceImpl userService;

  public UserController(UserServiceImpl userService) {
    this.userService = userService;
  }

  @GetMapping(value = "/")
  public String listOfUsers(ModelMap modelMap) {
    List<User> users = userService.getAllUser();
    modelMap.addAttribute("users", users);
    return "users";
  }

  @GetMapping(value = "/add")
  public String addUser(ModelMap modelMap) {
    modelMap.addAttribute("user", new User());
    return "add";
  }

  @GetMapping("/edit")
  public String editUser(@RequestParam("id") Long id, ModelMap modelMap) {
    User user = userService.findById(id);
    modelMap.addAttribute("user", user);
    return "edit";
  }

  @PostMapping(value = "/add")
  public String saveUser(@ModelAttribute @Valid User user, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "add";
    }
    userService.save(user);
    return "redirect:/";
  }

  @PostMapping(value = "/edit")
  public String updateUser(@RequestParam(name = "id") Long id, @ModelAttribute @Valid User user, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "edit";
    }
    userService.update(user);
    return "redirect:/";
  }

  @GetMapping(value = "/delete")
  public String deleteUser(@RequestParam("id") Long id) {
    userService.delete(id);
    return "redirect:/";
  }
}
