package cl.duoc.veterinaria.Controlller;

import cl.duoc.veterinaria.jwt.JWTAuthenticationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LoginController {

    // Servicio que construye y firma el JWT después de validar credenciales.
    @Autowired
    private JWTAuthenticationConfig jwtAuthenticationConfig;

    // Es el mismo origen de usuarios definido en WebSecurityConfig. El
    // Qualifier evita usar por accidente otro UserDetailsService, por ejemplo,
    // uno conectado a una base de datos.
    @Autowired
    @Qualifier("users")
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String login(
            @RequestParam("user") String username,
            @RequestParam("encryptedPass") String password) {
        try {
            // Busca al usuario de prueba por el valor recibido en Postman.
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // matches compara la clave en texto plano con el hash BCrypt guardado.
            // No se debe usar equals() para comparar contraseñas cifradas.
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
            }
            // Credenciales válidas: crea y devuelve el token para Authorization.
            return jwtAuthenticationConfig.getJWTToken(username);
        } catch (UsernameNotFoundException ex) {
            // No se revela si falló el usuario o la contraseña.
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }
    }
}
