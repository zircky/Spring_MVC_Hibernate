package zi.zircky.spring_mvc_hibernate.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zi.zircky.spring_mvc_hibernate.dao.UserDao;
import zi.zircky.spring_mvc_hibernate.model.Role;
import zi.zircky.spring_mvc_hibernate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

  private final UserDao userDao;
  private final BCryptPasswordEncoder getbCryptPasswordEncoder;
  private final RoleService roleService;

  @Autowired
  public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder getbCryptPasswordEncoder, RoleService roleService) {
    this.userDao = userDao;
    this.getbCryptPasswordEncoder = getbCryptPasswordEncoder;
    this.roleService = roleService;
  }

  @Override
  @Transactional
  public void createUser(User user) {
    if (user.getRoles() == null || user.getRoles().isEmpty()) {
      user.setRoles(roleService.findByName("ROLE_USER"));
    }
    user.setPassword(getbCryptPasswordEncoder.encode(user.getPassword()));
    userDao.save(user);
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
      user0.setUsername(user.getUsername());
      user0.setPassword(getbCryptPasswordEncoder.encode(user.getPassword()));
      user0.setEmail(user.getEmail());
      user0.setRoles(user.getRoles());
      userDao.save(user0);
    } catch (NullPointerException e) {
      throw new EntityNotFoundException();
    }
  }

  @Transactional
  @Override
  public User findByUsername(String username) {
    return userDao.findByUsername(username);
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

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userDao.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(String.format("User %s not found", username));
    }

    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
        mapRolesToAuthorities(user.getRoles()));
  }

  private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
  }
}
