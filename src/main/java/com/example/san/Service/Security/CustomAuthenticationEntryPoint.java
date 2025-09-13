package com.example.san.Service.Security;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

// Custom Authentication Entry Point
@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException) throws IOException {

    log.warn("Unauthorized access attempt to: {}", request.getRequestURI());

    String contentType = request.getContentType();
    String acceptHeader = request.getHeader("Accept");

    // Check if it's an API request
    if (request.getRequestURI().startsWith("/api/") ||
        (acceptHeader != null && acceptHeader.contains("application/json"))) {

      response.setContentType("application/json");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

      String jsonResponse = """
                {
                    "error": "Unauthorized",
                    "message": "Authentication required",
                    "status": 401,
                    "path": "%s"
                }
                """.formatted(request.getRequestURI());

      response.getWriter().write(jsonResponse);
    } else {
      // Redirect to login page for web requests
      response.sendRedirect("/login");
    }
  }
}
