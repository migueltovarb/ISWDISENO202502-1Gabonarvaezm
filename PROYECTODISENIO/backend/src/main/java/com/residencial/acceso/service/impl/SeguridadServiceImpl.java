package com.residencial.acceso.service.impl;

import com.residencial.acceso.model.Usuario;
import com.residencial.acceso.service.ISeguridadService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class SeguridadServiceImpl implements ISeguridadService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-minutes}")
    private int jwtExpirationMinutes;

    private final PasswordEncoder passwordEncoder;

    public SeguridadServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    @Override
    public String generarToken(Usuario usuario) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMinutes * 60 * 1000);

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("id", usuario.getId())
                .claim("rol", usuario.getRol().name())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String extraerEmail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    @Override
    public boolean esTokenValido(String token, Usuario usuario) {
        String email = extraerEmail(token);
        return email.equals(usuario.getEmail()) && !esTokenExpirado(token);
    }

    private boolean esTokenExpirado(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

    public boolean verificarPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String encriptarPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}