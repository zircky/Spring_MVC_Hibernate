package zi.zircky.spring_mvc_hibernate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zi.zircky.spring_mvc_hibernate.dao.RoleDao;
import zi.zircky.spring_mvc_hibernate.model.Role;

import java.util.Collection;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
  private RoleDao roleDao;

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
  public Collection<Role> findByName(String name) {
    return roleDao.findByName(name);
  }
}
