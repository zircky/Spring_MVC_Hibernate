package zi.zircky.spring_mvc_hibernate.service;

import zi.zircky.spring_mvc_hibernate.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
  void addRole(Role role);

  List<Role> getAllRoles();

  Set<Role> findByName(String name);

  Set<Role> findByIds(List<Long> ids);
}

