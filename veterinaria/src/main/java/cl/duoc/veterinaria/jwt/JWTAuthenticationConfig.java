package cl.duoc.veterinaria.jwt;


import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cl.duoc.veterinaria.jwt.Constants.*;

@Configuration
public class JWTAuthenticationConfig {

    public String getJWTToken(String username) {
        // Autoridad que quedará dentro del token. Después el filtro la recupera
        // para construir la autenticación del usuario en Spring Security.
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        // Los claims son los datos que se incluyen dentro del payload del JWT.
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        // Se crea el JWT con: claims, nombre de usuario, fechas y firma HMAC.
        // compact() lo transforma en el texto de tres partes separado por puntos.
        String token = Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1440))
                .and()
                .signWith(getSigningKey(SUPER_SECRET_KEY))
                .compact();

        // Se devuelve listo para copiarlo al header Authorization de Postman.
        return "Bearer " + token;
    }

}
