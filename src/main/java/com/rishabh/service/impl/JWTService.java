package com.rishabh.service.impl;

import com.rishabh.repository.UserRolesRepository;
import com.rishabh.entity.User;
import com.rishabh.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

/**
 * Service class for handling JWT (JSON Web Token) operations.
 */
@Service
public class JWTService {

    private final UserService userService;
    private final UserRolesRepository userRolesRepository;

    @Autowired
    public JWTService(UserService userService, UserRolesRepository userRolesRepository) {
        this.userService = userService;
        this.userRolesRepository = userRolesRepository;
    }

    // Secret key used for signing JWT tokens.
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Generate a JWT token for the given username.
     *
     * @param userName The username for which the token is generated.
     * @return The JWT token.
     */
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        User user = userService.findByUserName(userName);
        List<Integer> roles = userRolesRepository.findRoleIdsByUserUserId(user.getUserId());
        claims.put("roles", roles);
        return createToken(claims, userName);
    }

    /**
     * Create a JWT token with specified claims.
     *
     * @param claims   The claims to include in the token.
     * @param userName The subject (username) of the token.
     * @return The JWT token.
     */
    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuer("RISHABH")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Obtain the signing key from the provided secret.
     *
     * @return The signing key.
     */
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extract the username from a JWT token.
     *
     * @param token The JWT token.
     * @return The extracted username.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract role IDs from a JWT token.
     *
     * @param token The JWT token.
     * @return A list of role IDs.
     */
    public List<Integer> extractRolesIds(String token) {
        Claims claims = extractAllClaims(token);
        List<Integer> roles = claims.get("roles", List.class);
        return roles;
    }

    /**
     * Extract the token's expiration date.
     *
     * @param token The JWT token.
     * @return The expiration date.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract a specific claim from a JWT token.
     *
     * @param token           The JWT token.
     * @param claimsResolver  A function to extract a specific claim from the token.
     * @param <T>             The type of the claim.
     * @return The extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims from a JWT token.
     *
     * @param token The JWT token.
     * @return All the claims in the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Check if a JWT token has expired.
     *
     * @param token The JWT token.
     * @return `true` if the token has expired, `false` otherwise.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Validate a JWT token for a user.
     *
     * @param token       The JWT token.
     * @param userDetails The user details.
     * @return `true` if the token is valid for the user, `false` otherwise.
     */
    public Boolean validateUser(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Validate the format and signature of a JWT token.
     *
     * @param token The JWT token.
     */
    public void validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }
}
