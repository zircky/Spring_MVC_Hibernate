package zi.zircky.spring_mvc_hibernate.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zi.zircky.spring_mvc_hibernate.dto.LoginDto;
import zi.zircky.spring_mvc_hibernate.dto.RoleDto;
import zi.zircky.spring_mvc_hibernate.dto.UserDto;
import zi.zircky.spring_mvc_hibernate.model.Role;
import zi.zircky.spring_mvc_hibernate.service.RoleService;
import zi.zircky.spring_mvc_hibernate.service.user.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
  private final UserService userService;
  private final RoleService roleService;

  public AuthRestController(UserService service, RoleService roleService) {
    this.userService = service;
    this.roleService = roleService;
  }

  @GetMapping("/roles")
  public List<RoleDto> getRoles() {
    List<Role> roles = roleService.getAllRoles();
    List<RoleDto> dtos = roles.stream()
        .map(role -> new RoleDto(role.getId(), role.getName()))
        .collect(Collectors.toList());
    return dtos;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
    boolean isAuthenticated = userService.authenticate(loginDto.getEmail(), loginDto.getPassword());
    if (isAuthenticated) {
      return ResponseEntity.ok(Map.of("message", "Login successful"));
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
  }

  @PostMapping("/sign-up")
  public ResponseEntity<String> signUp(@RequestBody UserDto request) {
    try {
      System.out.println("Полученные данные: " + request);
      userService.register(request);
      return ResponseEntity.ok("Пользователь успешно зарегистрирован");
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}