package com.rishabh.controller;

import com.rishabh.dto.LoginDTO;
import com.rishabh.service.impl.JWTService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class responsible for handling user authentication and token generation.
 */
@RestController
@RequestMapping("/login")
@Validated
public class LoginController {

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(JWTService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Endpoint for user authentication and token generation.
     *
     * @param loginDTO The LoginDTO containing username and password.
     * @return A JWT token if authentication is successful; otherwise, "Invalid Credentials."
     */
    @PostMapping("/authenticate")
    public String authenticateUserAndGetToken(@RequestBody @Valid LoginDTO loginDTO) {
        if(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword())).isAuthenticated()) {
            return jwtService.generateToken(loginDTO.getUserName());
        }
        else {
            return HttpServletResponse.SC_UNAUTHORIZED + "Invalid Credentials.";
        }
    }

}
