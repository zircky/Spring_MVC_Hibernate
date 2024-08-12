package zi.zircky.spring_mvc_hibernate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import zi.zircky.spring_mvc_hibernate.config.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfiguration {

  private final SuccessUserHandler successUserHandler;
  private final BCryptPasswordEncoder getbCryptPasswordEncoder;
  private final UserDetailsService userDetailsService;

  public WebSecurityConfig(SuccessUserHandler successUserHandler, UserDetailsService userDetailsService, BCryptPasswordEncoder getbCryptPasswordEncoder) {
    this.userDetailsService = userDetailsService;
    this.getbCryptPasswordEncoder = getbCryptPasswordEncoder;
    this.successUserHandler = successUserHandler;
  }


  protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http.
        authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/", "/index", "/error").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().hasAnyRole("USER", "ADMIN")
        )
        .formLogin()
        .loginPage("/pages/login")
        .successHandler(successUserHandler)
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/")
        .and()
        .exceptionHandling((exceptions) -> exceptions.accessDeniedPage("/forbidden"));
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
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

    authenticationProvider.setPasswordEncoder(getbCryptPasswordEncoder);
    authenticationProvider.setUserDetailsService(userDetailsService);

    return authenticationProvider;
  }

//  @Bean
//  public SpringSecurityDialect springSecurityDialect() {
//    return new SpringSecurityDialect();
//  }
}

