package zi.zircky.spring_mvc_hibernate.service;

import zi.zircky.spring_mvc_hibernate.model.User;

import java.util.List;

public interface UserService {
  void save(User user);

  void update(User user);

  void delete(Long id);

  User findById(Long id);

  List<User> getAllUser();
}
