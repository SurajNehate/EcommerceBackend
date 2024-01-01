
package com.rishabh.filter;

import com.rishabh.repository.UserRolesRepository;
import com.rishabh.service.UserService;
import com.rishabh.service.impl.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

/**
 * This filter is responsible for handling JWT-based authentication for incoming HTTP requests.
 */
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    public static String CURRENT_USER ="";

    private final JWTService jwtService;
    private final UserService userService;
    private final UserRolesRepository userRolesRepository;
    @Autowired
    public JWTAuthFilter(JWTService jwtService, UserService userService, UserRolesRepository userRolesRepository) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.userRolesRepository = userRolesRepository;
    }

    /**
     * Filters incoming HTTP requests to authenticate users using JWT tokens.
     *
     * @param request     The HTTP request object.
     * @param response    The HTTP response object.
     * @param filterChain The filter chain for processing the request.
     * @throws ServletException If a servlet-related exception occurs.
     * @throws IOException      If an I/O - related exception occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extract the JWT token from the 'Authorization' header
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                // Validate the JWT token
                jwtService.validateToken(token);

                // Extract username and role IDs from the token
                String userName = jwtService.extractUsername(token);
                CURRENT_USER = userName;
                List<Integer> rolesIds = jwtService.extractRolesIds(token);

                // Retrieve user's roles from the database
                List<Integer> userRolesIds = userRolesRepository.findRoleIdsByUserName(userName);

                // Check if the user has all the required roles
                if (new HashSet<>(userRolesIds).containsAll(rolesIds)) {

                    // Authenticate the user if not already authenticated
                    if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = userService.loadUserByUsername(userName);
                        if (jwtService.validateUser(token, userDetails)) {
                            UsernamePasswordAuthenticationToken authenticationToken = new
                                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    }
                    filterChain.doFilter(request, response);
                    return;
                }

            } catch (Exception e) {
                throw new IllegalArgumentException("Token Expired");
            }

        }

        // Return an unauthorized response if the token is missing or invalid
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("Unauthorized Access");
    }

    // Exclude authentication endpoint from JWT validation
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().equalsIgnoreCase("/login/authenticate");
    }
}



