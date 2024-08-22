package zi.zircky.spring_mvc_hibernate.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import zi.zircky.spring_mvc_hibernate.model.User;

import zi.zircky.spring_mvc_hibernate.service.UserServiceImpl;

import java.io.IOException;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

  @Autowired
  private UserServiceImpl userService;

  public SuccessUserHandler() {
  }

  public void setUserService(UserServiceImpl userService) {
    this.userService = userService;
  }

  @Override
  @Transactional
  public void onAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    User user = userService.findByUsername(userDetails.getUsername());

    String redirectURL = request.getContextPath();
    System.out.println(redirectURL);

    if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
      redirectURL = "/admin";
      System.out.print(redirectURL);
    } else if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
      redirectURL = "/user";
      System.out.print(redirectURL);
    }

    response.sendRedirect(redirectURL);
  }

}
