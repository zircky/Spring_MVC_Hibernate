package zi.zircky.spring_mvc_hibernate;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import zi.zircky.spring_mvc_hibernate.dao.RoleDao;

@Component
public class AddUseAndRoleInTable {
  private final RoleDao roleDao;

  public AddUseAndRoleInTable(RoleDao roleDao) {
    this.roleDao = roleDao;
  }

  @PostConstruct
  private void init() {
//    roleDao.saveAndFlush(new Role("ADMIN"));
//    roleDao.saveAndFlush(new Role("USER"));

  }
}
