package zi.zircky.spring_mvc_hibernate.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zi.zircky.spring_mvc_hibernate.model.Role;

import java.util.Set;

public interface RoleDao extends JpaRepository<Role, Long> {
  Set<Role> findByName(String name);

}
