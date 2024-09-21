package zi.zircky.spring_mvc_hibernate.service;

import zi.zircky.spring_mvc_hibernate.model.Role;

import java.util.Collection;
import java.util.List;

public interface RoleService {
  void addRole(Role role);

  List<Role> getAllRoles();

  Collection<Role> findByName(String name);

}

