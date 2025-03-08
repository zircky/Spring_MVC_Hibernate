package zi.zircky.spring_mvc_hibernate.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zi.zircky.spring_mvc_hibernate.dto.UserDto;
import zi.zircky.spring_mvc_hibernate.model.User;
import zi.zircky.spring_mvc_hibernate.service.RoleService;
import zi.zircky.spring_mvc_hibernate.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
  private final UserService userService;
  private final RoleService roleService;

  public AdminRestController(UserService userService, RoleService roleService) {
    this.userService = userService;
    this.roleService = roleService;
  }

  @GetMapping()
  public ResponseEntity<List<User>> getAllUsers() {
    return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
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

  @PutMapping("/editUser/{id}")
  public ResponseEntity<?> editUser(@RequestBody User user, @RequestParam(value = "roles") List<Long> roleIds, @PathVariable Long id) {
    try {
      user.setRoles(roleService.findByIds(roleIds));
      userService.updateUser(id, user);
      return ResponseEntity.ok("Пользователь успешно изменен");
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    }
  }
}
