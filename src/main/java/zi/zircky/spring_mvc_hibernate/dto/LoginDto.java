package zi.zircky.spring_mvc_hibernate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class LoginDto {
  @Email
  @NotBlank
  String email;
  @NotBlank
  String password;
}