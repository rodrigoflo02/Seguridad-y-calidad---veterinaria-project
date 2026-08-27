package cl.duoc.veterinaria.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import static cl.duoc.veterinaria.jwt.Constants.*;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private Claims setSigningKey(HttpServletRequest request) {
        // Extrae el JWT quitando el prefijo "Bearer " del header Authorization.
        String jwtToken = request.
                getHeader(HEADER_AUTHORIZACION_KEY).
                replace(TOKEN_BEARER_PREFIX, "");

        // Verifica que la firma coincida con la clave secreta y obtiene el payload.
        // Si el token fue alterado o venció, JJWT lanza una excepción.
        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey(SUPER_SECRET_KEY))
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();

    }

    private void setAuthentication(Claims claims) {

        // Obtiene las autoridades guardadas al generar el token.
        List<String> authorities = (List<String>) claims.get("authorities");

        // Crea la identidad autenticada que Spring Security usará durante
        // esta solicitud. No guarda una sesión en el servidor.
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    private boolean isJWTValid(HttpServletRequest request, HttpServletResponse res) {
        // Esta validación inicial comprueba solo el formato; la firma se valida
        // posteriormente en setSigningKey().
        String authenticationHeader = request.getHeader(HEADER_AUTHORIZACION_KEY);
        if (authenticationHeader == null || !authenticationHeader.startsWith(TOKEN_BEARER_PREFIX))
            return false;
        return true;
    }

    @Override
    protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request, @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain) throws ServletException, IOException {
        try {
            // El filtro se ejecuta en cada solicitud antes de decidir si la ruta
            // protegida puede ser atendida.
            if (isJWTValid(request, response)) {
                Claims claims = setSigningKey(request);
                if (claims.get("authorities") != null) {
                    setAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            // Continúa hacia el controlador. Si la ruta requiere autenticación
            // y el contexto quedó vacío, Spring devuelve 401.
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            // Un token inválido no debe llegar al controlador protegido.
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            return;
        }
    }

}
