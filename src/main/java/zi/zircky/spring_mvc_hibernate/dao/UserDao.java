package zi.zircky.spring_mvc_hibernate.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zi.zircky.spring_mvc_hibernate.model.User;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
  //  @Query("Select u from User u left join fetch u.roles where u.email=:email")
  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);
}
