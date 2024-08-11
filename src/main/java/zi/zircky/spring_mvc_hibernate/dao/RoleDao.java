package zi.zircky.spring_mvc_hibernate.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zi.zircky.spring_mvc_hibernate.model.Role;

import java.util.Collection;

public interface RoleDao extends JpaRepository<Role, Long> {
  Collection<Role> findByName(String name);
}
