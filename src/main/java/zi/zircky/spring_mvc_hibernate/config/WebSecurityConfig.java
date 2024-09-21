package zi.zircky.spring_mvc_hibernate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfiguration {

  private final BCryptPasswordEncoder getbCryptPasswordEncoder;
  private final UserDetailsService userDetailsService;

  private HttpSecurity httpSecurity;

  public WebSecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder getbCryptPasswordEncoder) {
    this.userDetailsService = userDetailsService;
    this.getbCryptPasswordEncoder = getbCryptPasswordEncoder;
  }

  public void setHttpSecurity(HttpSecurity httpSecurity) {
    this.httpSecurity = httpSecurity;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests((authorize) -> {
          try {
            authorize
                .requestMatchers("/", "/index", "/sign-up", "/login", "/error").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated();
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        }
    ).securityContext((securityContext) -> securityContext
        .requireExplicitSave(false));

    http.formLogin(form -> {
      try {
        form.loginPage("/login").permitAll()
            .successHandler(successHandler());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    http.logout().logoutSuccessUrl("/login?logout");

    http.exceptionHandling((exceptions) -> exceptions.accessDeniedPage("/forbidden"));
    return http.build();
  }

//  @Bean
//  public JdbcUserDetailsManager users(DataSource dataSource) {
//    UserDetails userDetails = User.builder()
//        .username("user")
//        .password("{bcrypt}$2a$12$e8bJMGMgpqnznZPnkrqb1OA5WkI2/R06sXOxfgn.mw3kZpQwAUvjO")
//        .roles("USER", "ADMIN")
//        .build();
//
//    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//    if (jdbcUserDetailsManager.userExists(userDetails.getUsername())) {
//      jdbcUserDetailsManager.deleteUser(userDetails.getUsername());
//    }
//    jdbcUserDetailsManager.createUser(userDetails);
//    return jdbcUserDetailsManager;
//
//  }

  @Bean
  public AuthenticationSuccessHandler successHandler() {
    return new SuccessUserHandler();
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

    authenticationProvider.setPasswordEncoder(getbCryptPasswordEncoder);
    authenticationProvider.setUserDetailsService(userDetailsService);

    return authenticationProvider;
  }

}

