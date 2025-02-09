package zi.zircky.spring_mvc_hibernate.config;

import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import zi.zircky.spring_mvc_hibernate.service.user.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfiguration {

  private final BCryptPasswordEncoder getbCryptPasswordEncoder;
  private final UserService userService;

  @Setter
  private HttpSecurity httpSecurity;

  public WebSecurityConfig(UserService userService, BCryptPasswordEncoder getbCryptPasswordEncoder) {
    this.userService = userService;
    this.getbCryptPasswordEncoder = getbCryptPasswordEncoder;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorize -> {
      try {
        authorize
            .requestMatchers("/", "/index", "/sign-up", "/login", "/error").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
            .anyRequest().authenticated();
      } catch (Exception e) {
        throw new IllegalArgumentException(e);
      }
    }).securityContext(securityContext -> securityContext
        .requireExplicitSave(false));

    http.formLogin(form -> {
      try {
        form.loginPage("/login").permitAll()
            .successHandler(successHandler());
      } catch (Exception e) {
        throw new IllegalArgumentException(e);
      }
    });

    http.logout().logoutSuccessUrl("/login");

    http.exceptionHandling(exceptions -> exceptions.accessDeniedPage("/forbidden"));
    return http.build();
  }


  @Bean
  public AuthenticationSuccessHandler successHandler() {
    return new SuccessUserHandler();
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

    authenticationProvider.setPasswordEncoder(getbCryptPasswordEncoder);
    authenticationProvider.setUserDetailsService(this.userService);

    return authenticationProvider;
  }

}
