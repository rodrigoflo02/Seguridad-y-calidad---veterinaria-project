package cl.duoc.veterinaria.Security;

import cl.duoc.veterinaria.jwt.Constants;
import cl.duoc.veterinaria.jwt.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // Filtro propio que lee y valida el JWT recibido en cada request.
    @Autowired
    private JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Define las reglas que Spring Security aplicará a las rutas HTTP.
        http
                // Las peticiones de Postman no usan token CSRF. Para una API JWT
                // sin sesión se desactiva; los formularios web sí requieren otras
                // medidas de protección en una aplicación productiva.
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        // Endpoint de la guía: entrega un token JWT.
                        .requestMatchers(HttpMethod.POST, Constants.LOGIN_URL).permitAll()
                        // Rutas públicas: no exigen JWT ni login previo.
                        .requestMatchers("/", "/home", "/login", "/css/**").permitAll()
                        // Toda ruta que no coincida arriba exige autenticación.
                        .anyRequest().authenticated())
                // Inserta la comprobación del JWT en la cadena de filtros de Spring.
                .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form
                        // Muestra el formulario HTML al visitar GET /login.
                        .loginPage("/login")
                        // Evita que Spring intercepte el POST /login usado por JWT.
                        .loginProcessingUrl("/web/login")
                        .permitAll())
                .logout(logout -> logout.permitAll());
        return http.build();
    }

    @Bean
    @Description("Cuentas de prueba para el login web y el endpoint JWT")
    public UserDetailsService users() {
        // Usuarios de prueba almacenados en memoria. Se eliminan al reiniciar.
        // User.builder() cifra las claves con BCrypt antes de guardarlas.
        UserDetails veterinario = User.builder()
                .username("Veterinario")
                .password(passwordEncoder().encode("vet123"))
                .roles("VET")
                .build();
        UserDetails admin = User.builder()
                .username("Admin")
                .password(passwordEncoder().encode("S0p0rte"))
                .roles("ADMIN")
                .build();
        UserDetails recepcion = User.builder()
                .username("Recepcion")
                .password(passwordEncoder().encode("recepcion123"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(veterinario, admin, recepcion);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Bean reutilizable para cifrar y comprobar contraseñas mediante BCrypt.
        return new BCryptPasswordEncoder();
    }
}
