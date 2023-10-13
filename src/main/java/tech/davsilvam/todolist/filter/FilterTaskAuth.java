package tech.davsilvam.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.davsilvam.todolist.user.IUserRepository;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    var servletPath = request.getServletPath();

    if (servletPath.startsWith("/tasks/")) {
      var authorization = request.getHeader("Authorization");

      var encodedAuth = authorization.substring("Basic".length()).trim();
      byte[] decodedAuth = Base64.getDecoder().decode(encodedAuth);

      String[] credentials = new String(decodedAuth).split(":");

      String username = credentials[0];
      String password = credentials[1];

      var user = userRepository.findByUsername(username);

      if (user == null) {
        response.sendError(401);
        return;
      }

      var passwordMatch = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

      if (passwordMatch.verified == false) {
        response.sendError(401);
        return;
      }

      request.setAttribute("userId", user.getId());
    }

    filterChain.doFilter(request, response);
  }

}
