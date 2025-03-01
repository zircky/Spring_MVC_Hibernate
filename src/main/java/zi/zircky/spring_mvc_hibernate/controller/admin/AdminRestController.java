package zi.zircky.spring_mvc_hibernate.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zi.zircky.spring_mvc_hibernate.dto.UserDto;
import zi.zircky.spring_mvc_hibernate.service.RoleService;
import zi.zircky.spring_mvc_hibernate.service.user.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
  private final UserService userService;
  private final RoleService roleService;

  public AdminRestController(UserService userService, RoleService roleService) {
    this.userService = userService;
    this.roleService = roleService;
  }

  @PostMapping("/new")
  public ResponseEntity<?> newCreate(@RequestBody UserDto userDto) {
    try {
      System.out.println("Полученнные данные: " + userDto);
      userService.register(userDto);
      return ResponseEntity.ok("Пользователь успешно добавлен");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
