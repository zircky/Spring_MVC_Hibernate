package zi.zircky.spring_mvc_hibernate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zi.zircky.spring_mvc_hibernate.dao.RoleDao;
import zi.zircky.spring_mvc_hibernate.model.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
  private final RoleDao roleDao;

  @Autowired
  public RoleServiceImpl(RoleDao roleDao) {
    this.roleDao = roleDao;
  }

  @Override
  public void addRole(Role role) {
    roleDao.save(role);
  }

  @Override
  public List<Role> getAllRoles() {
    return roleDao.findAll();
  }

  @Override
  public Set<Role> findByName(String name) {
    return roleDao.findByName(name);
  }

  @Override
  public Set<Role> findByIds(List<Long> ids) {
    return new HashSet<>(roleDao.findAllById(ids));
  }
}
