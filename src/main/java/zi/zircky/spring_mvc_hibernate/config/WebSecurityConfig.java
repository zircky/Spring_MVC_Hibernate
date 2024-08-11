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
import zi.zircky.spring_mvc_hibernate.config.PasswordEncoder;

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


  protected void configure(HttpSecurity http) throws Exception {
    http.
        authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/", "/index", "/error").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().hasAnyRole("USER", "ADMIN")
        )
        .formLogin().loginPage("/login.html").successHandler(successUserHandler)
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/")
        .and()
        .exceptionHandling()
        .accessDeniedPage("/forbidden");
  }

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

