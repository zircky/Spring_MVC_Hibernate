package zi.zircky.spring_mvc_hibernate.dto;

import lombok.Data;

@Data
public class RoleDto {
  Long id;
  String name;

  public RoleDto(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}