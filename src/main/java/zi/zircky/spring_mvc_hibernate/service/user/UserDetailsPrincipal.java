package zi.zircky.spring_mvc_hibernate.service.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import zi.zircky.spring_mvc_hibernate.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public class UserDetailsPrincipal implements UserDetails {
  private final Optional<User> user;

  public UserDetailsPrincipal(Optional<User> user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> roles = new ArrayList<>();

    user.get().getRoles().forEach(role -> {
      roles.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
    });

    return roles;
  }

  @Override
  public String getPassword() {
    return user.get().getPassword();
  }

  @Override
  public String getUsername() {
    return user.get().getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
