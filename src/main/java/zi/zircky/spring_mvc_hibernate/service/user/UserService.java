package zi.zircky.spring_mvc_hibernate.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import zi.zircky.spring_mvc_hibernate.dto.UserDto;
import zi.zircky.spring_mvc_hibernate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
  User createUser(User user);

  Optional<User> getUserByEmail(String usernames);

  boolean authenticate(String email, String password);

  void delete(Long id);

  User findById(Long id);

  void updateUser(Long id, User user);

  User readUserById(Long id);

  List<User> getAllUser();

  void register(UserDto request);
}
