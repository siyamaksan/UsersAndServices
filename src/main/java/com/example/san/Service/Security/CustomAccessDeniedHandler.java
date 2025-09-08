package com.example.san.Service.Security;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

// Custom Access Denied Handler
@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {

    log.warn("Access denied for user {} to resource: {}",
        request.getRemoteUser(), request.getRequestURI());

    if (request.getRequestURI().startsWith("/api/")) {
      response.setContentType("application/json");
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);

      String jsonResponse = """
                {
                    "error": "Access Denied",
                    "message": "Insufficient privileges",
                    "status": 403,
                    "path": "%s"
                }
                """.formatted(request.getRequestURI());

      response.getWriter().write(jsonResponse);
    } else {
      response.sendRedirect("/access-denied");
    }
  }
}