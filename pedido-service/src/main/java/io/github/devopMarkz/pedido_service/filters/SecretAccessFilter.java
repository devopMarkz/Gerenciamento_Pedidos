package io.github.devopMarkz.pedido_service.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecretAccessFilter extends OncePerRequestFilter {

    private static final String SECRET_HEADER = "X-Secret-Access";
    private static final String EXPECTED_SECRET = "secret-access-microservice";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String xSecretHeader = request.getHeader(SECRET_HEADER);

        if (!EXPECTED_SECRET.equals(xSecretHeader)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Access denied. Invalid header.");
            return;
        }

        filterChain.doFilter(request, response);
    }

}
