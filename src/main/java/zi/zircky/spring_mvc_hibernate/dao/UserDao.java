package zi.zircky.spring_mvc_hibernate.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zi.zircky.spring_mvc_hibernate.model.User;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
}
