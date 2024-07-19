package zi.zircky.spring_mvc_hibernate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zi.zircky.spring_mvc_hibernate.dao.UserDao;
import zi.zircky.spring_mvc_hibernate.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  private final UserDao userDao;

  @Autowired
  public UserServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  @Transactional
  public User save(User user) {
    return userDao.save(user);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    userDao.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public User findById(Long id) {
    return userDao.findById(id).orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> getAllUser() {
    return userDao.findAll();
  }
}
