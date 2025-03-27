package com.ufps.gidisoft.config.jwt;

import com.ufps.gidisoft.entities.User;
import com.ufps.gidisoft.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String SECRET_KEY = "P1R2U3E4B5A6T7E8C9N0I1C2A3N4E5L6U7M8B9OC0M1P2A3N4Y";
    private static final long EXPIRATION_TIME = ((6 * 60) * 60) * 1000L;

    private final UserRepository userRepository;

    public String getToken(UserDetails usuario){
        Map<String, Object> extraClaims = new HashMap<>();
        User user = userRepository.findByUsercode(usuario.getUsername()).orElseThrow(()
                -> new RuntimeException("Usuario no encontrado"));
        extraClaims.put("idUser", user.getId());
        return getToken(extraClaims, usuario);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails usuario) {

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getToken(){
        return (Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes::getRequest)
                .map(request -> request.getHeader("Authorization"))
                .orElseThrow(() -> new RuntimeException("Requiere autenticación por token"))).substring(7);
    }

    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Date getIssuedFromToken(String token){
        return getClaim(token, Claims::getIssuedAt);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    private Claims getAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    public Integer getIdUsuario(){
        return getClaim(getToken(), claims -> claims.get("idUser", Integer.class));
    }

    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
}
