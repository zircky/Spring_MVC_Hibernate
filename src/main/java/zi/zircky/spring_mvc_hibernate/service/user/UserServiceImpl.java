package zi.zircky.spring_mvc_hibernate.service.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zi.zircky.spring_mvc_hibernate.dao.RoleDao;
import zi.zircky.spring_mvc_hibernate.dao.UserDao;
import zi.zircky.spring_mvc_hibernate.dto.UserDto;
import zi.zircky.spring_mvc_hibernate.model.Role;
import zi.zircky.spring_mvc_hibernate.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

  private final UserDao userDao;
  private final BCryptPasswordEncoder getbCryptPasswordEncoder;
  private final RoleDao roleDao;

  @Autowired
  public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder getbCryptPasswordEncoder, RoleDao roleDao) {
    this.userDao = userDao;
    this.getbCryptPasswordEncoder = getbCryptPasswordEncoder;
    this.roleDao = roleDao;
  }

  @Override
  @Transactional
  public User createUser(User user) {
    Set<Role> roles = new HashSet<>();
    user.setPassword(getbCryptPasswordEncoder.encode(user.getPassword()));
    user.getRoles().forEach(role -> {
      if (role.getId() > 0) {
        Role nRole = roleDao.findById(role.getId()).get();
        nRole.getUsers().add(user);
        roles.add(nRole);
      } else {
        roles.add(role);
      }
    });
    user.setRoles(roles);

    return userDao.save(user);
  }

  @Override
  public User readUserById(Long id) {
    Optional<User> user = userDao.findById(id);
    return user.orElse(new User());
  }

  @Transactional
  @Override
  public void updateUser(Long id, User user) {
    try {
      User user0 = readUserById(id);
      user0.setPassword(getbCryptPasswordEncoder.encode(user.getPassword()));
      user0.setFirstName(user.getFirstName());
      user0.setLastName(user.getLastName());
      user0.setAge(user.getAge());
      user0.setEmail(user.getEmail());
      user0.setRoles(user.getRoles());
      userDao.save(user0);
    } catch (NullPointerException e) {
      throw new EntityNotFoundException();
    }
  }

  @Transactional
  @Override
  public Optional<User> getUserByEmail(String email) {
    return userDao.findByEmail(email);
  }

  @Override
  public boolean authenticate(String email, String password) {
    Optional<User> userOptional = userDao.findByEmail(email);
    return userOptional.isPresent() && getbCryptPasswordEncoder.matches(password, userOptional.get().getPassword());
  }

  @Override
  @Transactional
  public void delete(Long userId) {
    User user = userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

    for (Role role : user.getRoles()) {
      role.getUsers().remove(user);
    }
    user.getRoles().clear();

    userDao.delete(user);
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

  @Override
  public void register(UserDto request) {
    validateRequest(request);
    User user = mapToUser(request);
    addRoleToUser(user, request.getRoles());

    System.out.println("Пользователь перед сохранением: " + user);
    userDao.save(user);

  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userDao.findByEmail(username);
    return new UserDetailsPrincipal(user);
  }

  private void validateRequest(UserDto request) {
    if (userDao.existsByEmail(request.getEmail())) {
      throw new IllegalArgumentException("Email уже используется");
    }
  }

  private User mapToUser(UserDto requsest) {
    User user = new User();
    user.setFirstName(requsest.getFirstName());
    user.setLastName(requsest.getLastName());
    user.setAge(requsest.getAge());
    user.setEmail(requsest.getEmail());
    user.setPassword(getbCryptPasswordEncoder.encode(requsest.getPassword()));

    return user;
  }

  private void addRoleToUser(User user, Long roleId) {
    Role role = roleDao.findById(roleId).orElseThrow(() -> new RuntimeException("Роль не найдена"));

    user.getRoles().add(role);
  }


}
