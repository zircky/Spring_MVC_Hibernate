package zi.zircky.spring_mvc_hibernate.controller;

import jakarta.validation.Valid;
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

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(value = "/")
  public String getAll(Model model) {
    model.addAttribute("users", userService.getAllUser());
    return "users";
  }

  @RequestMapping(value = "/createUser", method = RequestMethod.GET)
  public String createUser(Model model) {
    model.addAttribute("user", new User());
    return "/add";
  }

  @PostMapping("/createUser")
  public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "/add";
    }
    userService.save(user);
    return "redirect:/users";
  }

  @GetMapping("/users/${id}/edit")
  public String editUser(@RequestParam("id") Long id, Model model) {
    model.addAttribute("user", userService.findById(id));
    return "/edit";
  }

  @PatchMapping("/allUsers/{id}")
  public String updateUser(@ModelAttribute("user") User user,@RequestParam("id") Long id ) {
    userService.update(user);
    return "redirect:/users";
  }

  @DeleteMapping("/allUsers/delete/{id}")
  public String deleteUser( @RequestParam("id") Long id) {
    userService.delete(id);
    return "redirect:/users";
  }

}
