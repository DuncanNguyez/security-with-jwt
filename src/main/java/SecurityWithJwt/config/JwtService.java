package SecurityWithJwt.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String extractUsername(String token) {
        return extractPayloadProperty(token, Claims::getSubject);
    }

    public <T> T extractPayloadProperty(String token, Function<Claims, T> claimsResolve) {
        Claims claims = extractClaims(token);
        return claimsResolve.apply(claims);
    }

    public String generateToken(UserDetails user) {
        return buildToken(user,new HashMap<>(),jwtExpiration);
    }

    public String generateToken(UserDetails user, Map<String, Objects> payload) {
        return buildToken(user, payload, jwtExpiration);
    }

    public String generateRefreshToken (UserDetails user){
        return buildToken(user,new HashMap<>(),refreshExpiration);
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractPayloadProperty(token, Claims::getExpiration);
    }

    private String buildToken(UserDetails user, Map<String, Objects> payload, long jwtExpiration) {
        return Jwts.builder()
                .claims(payload)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(generateKey())
                .compact();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }


}
