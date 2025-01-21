package zi.zircky.spring_mvc_hibernate.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

//  @Column(name = "username")
//  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "age")
  private int age;

  @Column(name = "email")
  private String email;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  @Fetch(FetchMode.JOIN)
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "roles_id"))
  private Set<Role> roles = new HashSet<>();

  public User() {
  }

  public static String getStringRoles(Collection<Role> roles) {
    return roles.stream()
        .map(role -> role.getAuthority().replaceAll("ROLE_", ""))
        .collect(Collectors.joining(", "));
  }


  @Override
  public String toString() {
    return "User [id=" + id +
        ", password=" + password +
        ", firstName=" + firstName +
        ", lastName=" + lastName +
        ", age=" + age +
        ", email=" + email + "]";
  }
}
