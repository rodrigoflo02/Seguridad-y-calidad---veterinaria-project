package cl.duoc.veterinaria.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

public class Constants {

    // Ruta a la que Postman envía usuario y contraseña para solicitar un JWT.
    public static final String LOGIN_URL = "/login";
    // Nombre estándar del encabezado HTTP que transporta el token.
    public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
    // Prefijo que debe acompañar al JWT: "Authorization: Bearer <token>".
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    // Datos usados al crear o comprobar la firma del token.
    // En una aplicación real la clave debe estar en una variable de entorno,
    // nunca escrita directamente en el repositorio.
    public static final String ISSUER_INFO = "https://www.duocuc.cl/";
    public static final String SUPER_SECRET_KEY = "ZnJhc2VzbGFyZ2FzcGFyYWNvbG9jYXJjb21vY2xhdmVlbnVucHJvamVjdG9kZWVtZXBsb3BhcmFqd3Rjb25zcHJpbmdzZWN1cml0eQ==bWlwcnVlYmFkZWVqbXBsb3BhcmFiYXNlNjQ=";
    public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day

    public static Key getSigningKeyB64(String secret) {
        // Variante para una clave que fue almacenada codificada en Base64.
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static Key getSigningKey(String secret) {
        // Convierte el texto secreto en la clave HMAC usada para firmar y validar.
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
