package com.lianaa98.roomy.utils;

import com.lianaa98.roomy.models.User;
import com.lianaa98.roomy.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

@Component
public class JwtUtils {

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;

    public String getSecretKey() {
        return SECRET_KEY;
    }

    public long getExpirationTime() {
        return EXPIRATION_TIME;
    }

    public String generateToken(String username) {
        User user = userRepository.findByUsername(username);
        Long id = user.getId();

        Map<String, Object> claims = new HashMap<>();

        claims.put("userId", userRepository.findByUsername(username).getId());
        claims.put("created", new Date());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public Long extractUserId(String jwt) {
        String token = jwt.substring(7);
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }

    public Boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
    public Boolean validateToken(String jwt) {
        String token = jwt.substring(7);
        return !isTokenExpired(token);
    }

    public User getUserFromToken(String jwt) {
        if (!validateToken(jwt)) {
            return null;
        }
        Long userId = extractUserId(jwt);
        return userRepository.findById(userId).orElse(null);
    }

}
