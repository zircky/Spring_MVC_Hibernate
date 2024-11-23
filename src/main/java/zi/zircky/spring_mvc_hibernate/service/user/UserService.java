package zi.zircky.spring_mvc_hibernate.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import zi.zircky.spring_mvc_hibernate.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
  User
  createUser(User user);

  User findByUsername(String usernames);

  boolean delete(Long id);

  User findById(Long id);

  void updateUser(Long id, User user);

  User readUserById(Long id);

  List<User> getAllUser();
}
