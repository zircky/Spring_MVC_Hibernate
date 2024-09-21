package zi.zircky.spring_mvc_hibernate.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import zi.zircky.spring_mvc_hibernate.model.User;

import java.io.IOException;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    User user = (User) authentication.getPrincipal();
    String redirectURL = request.getContextPath();

    if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
      redirectURL = "/admin";
    } else if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
      redirectURL = "/user";
    }

    response.sendRedirect(redirectURL);
  }
}
