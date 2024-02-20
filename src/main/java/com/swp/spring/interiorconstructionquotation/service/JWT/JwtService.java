package com.swp.spring.interiorconstructionquotation.service.JWT;

import com.swp.spring.interiorconstructionquotation.dao.IUserRepository;
import com.swp.spring.interiorconstructionquotation.entity.Role;
import com.swp.spring.interiorconstructionquotation.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtService {
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    @Autowired
    private IUserRepository iUserRepository;

    //Tao JWT dua tren ten dang nhap
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        User user = iUserRepository.findByUsername(username);
        int userId = user.getUserId();
        boolean enabled = user.isEnabled();
        String role = "";
        if (user != null && ! user.getRoles().isEmpty()){
            List<Role> list = user.getRoles().stream().toList();
            role = list.get(0).getName();
        }
        claims.put("id", userId);
        claims.put("role", role);
        claims.put("enabled", enabled);
        return createToken(claims, username);
    }

    //Tao JWT voi cac claim da chon
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // JWT het han sau 30p
                .signWith(SignatureAlgorithm.HS256, getSignedKey())
                .compact();
    }

    //Lay secret key
    private Key getSignedKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Trich xuat thong tin
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSignedKey()).parseClaimsJws(token).getBody();
    }

    //Trich xuat thong tin cho 1 claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    //Kiem tra thoi gian het han tu JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //Kiem tra username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //Kiem tra cai JWT da het han
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //Kiem tra tinh hop le
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
