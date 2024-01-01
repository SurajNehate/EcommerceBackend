package com.rishabh.config;

import com.rishabh.filter.JWTAuthFilter;
import com.rishabh.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * Configuration class for user security settings.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class UserSecurityConfig  {

    private final JWTAuthFilter jwtAuthFilter;
    private final UserService userService;


    @Autowired
    public UserSecurityConfig(JWTAuthFilter jwtAuthFilter , UserService userService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userService = userService;
    }

    /**
     * Creates a BCrypt password encoder bean.
     *
     * @return BCryptPasswordEncoder bean.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @InitBinder
//    public void initBinder(WebDataBinder dataBinder) {
//        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
//        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
//    }

    /**
     * Configures the security filter chain for HTTP requests.
     *
     * @param http HttpSecurity object to configure.
     * @return SecurityFilterChain for HTTP requests.
     * @throws Exception If configuration fails.
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
                        http.authenticationProvider(daoAuthenticationProvider())
                                .addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class).
                                authorizeHttpRequests(
                                        configurer -> configurer
                                                .requestMatchers("/login/authenticate").permitAll()
                        .anyRequest().authenticated()
                );
        http.csrf(
                AbstractHttpConfigurer::disable);
        http.exceptionHandling(
                exceptions -> exceptions
                        .authenticationEntryPoint(unauthorizedEntryPoint())
        );
        return http.build();
    }
    /**
     * Creates an unauthorized entry point for handling authentication errors.
     *
     * @return AuthenticationEntryPoint for unauthorized access.
     */
    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) ->
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    /**
     * Configures the DaoAuthenticationProvider with user details service and password encoder.
     *
     * @return DaoAuthenticationProvider for authentication.
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }
    /**
     * Creates an AuthenticationManager bean.
     *
     * @param authenticationConfiguration AuthenticationConfiguration for obtaining the AuthenticationManager.
     * @return AuthenticationManager bean.
     * @throws Exception If configuration fails.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration  authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
