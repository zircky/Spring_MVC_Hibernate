package zi.zircky.spring_mvc_hibernate.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class SuccessUserHandler extends SimpleUrlAuthenticationSuccessHandler {

  private static final Logger logger = LoggerFactory.getLogger(SuccessUserHandler.class);

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
    logger.info("User successfully authenticated: {}", authentication.getName());
    logger.info("Authorities: {}", authentication.getAuthorities());

    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    String redirectUrl = authorities.stream()
        .map(GrantedAuthority::getAuthority)
        .filter(role -> role.equals("ROLE_ADMIN") || role.equals("ROLE_USER"))
        .findFirst()
        .map(role -> role.equals("ROLE_ADMIN") ? "/admin" : "/user")
        .orElse("/");

    logger.info("Redirecting to: {}", redirectUrl);
    response.sendRedirect(redirectUrl);
  }
}
