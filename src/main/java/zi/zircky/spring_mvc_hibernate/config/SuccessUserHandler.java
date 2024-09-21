package zi.zircky.spring_mvc_hibernate.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import zi.zircky.spring_mvc_hibernate.service.UserService;

import java.io.IOException;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

  @Autowired
  private UserService userService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    org.springframework.security.core.userdetails.User userDetails =
        (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

    // Fetch your custom User entity from the database
    String username = userDetails.getUsername();
    zi.zircky.spring_mvc_hibernate.model.User customUser = userService.findByUsername(username);

    // Determine redirect URL based on roles
    String redirectURL = request.getContextPath();

    if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
      redirectURL = "/admin";
    } else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
      redirectURL = "/user";
    }

    // Optionally, you can store the customUser in session or use it as needed
    request.getSession().setAttribute("loggedUser", customUser);

    response.sendRedirect(redirectURL);
  }
}
