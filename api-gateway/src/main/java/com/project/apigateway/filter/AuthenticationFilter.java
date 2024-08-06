package com.project.apigateway.filter;

import com.project.apigateway.util.JwtUtil;
import com.project.apigateway.util.RouteValidator;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

  @Autowired
  private RouteValidator validator;

  @Autowired
  private JwtUtil jwtUtil;

  public AuthenticationFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return ((exchange, chain) -> {
      if (validator.isSecured.test(exchange.getRequest())) {
        //header contains token or not
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
          throw new RuntimeException("missing authorization header");
        }

        String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
          authHeader = authHeader.substring(7);
        }
        try {
          jwtUtil.validateToken(authHeader);

          Claims claims = jwtUtil.getClaimsFromToken(authHeader);
          String username = claims.getSubject();
          List<String> roles = claims.get("roles", List.class);

          // Add roles to headers
          ServerHttpRequest request = exchange.getRequest().mutate()
            .header("userRole", roles.get(0))
            .header("userName", username)
            .build();

          // Continue the filter chain with the modified request
          return chain.filter(exchange.mutate().request(request).build());

        } catch (Exception e) {
          System.out.println("invalid access...!");
          throw new RuntimeException("un authorized access to application");
        }
      }
      return chain.filter(exchange);
    });
  }

  public static class Config {

  }
}
