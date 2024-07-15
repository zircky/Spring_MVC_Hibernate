package zi.zircky.spring_mvc_hibernate.dao;

import zi.zircky.spring_mvc_hibernate.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    void save(User user);

    void update(User user);

    void delete(Long id);

    User findById(Long id);
}
