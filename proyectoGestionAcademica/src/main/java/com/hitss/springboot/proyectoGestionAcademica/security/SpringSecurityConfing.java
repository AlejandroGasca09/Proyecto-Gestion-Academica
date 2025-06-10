package com.hitss.springboot.proyectoGestionAcademica.security;

import com.hitss.springboot.proyectoGestionAcademica.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Habilita la seguridad web de Spring Security
@EnableMethodSecurity // Habilita la seguridad a nivel de método (@PreAuthorize, @PostAuthorize, etc.)
public class SpringSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter; // Este filtro lo crearemos a continuación

    // Inyectamos las dependencias necesarias
    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService,
                                JwtAuthenticationFilter jwtAuthFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    // Define el PasswordEncoder a utilizar (ya lo tenías)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configura el AuthenticationProvider, que es quien sabe cómo buscar usuarios y cómo cifrar contraseñas
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService); // Usa nuestro CustomUserDetailsService
        authProvider.setPasswordEncoder(passwordEncoder()); // Usa el PasswordEncoder definido
        return authProvider;
    }

    // Expone el AuthenticationManager como un Bean, necesario para autenticar usuarios en el AuthService
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Cadena de filtros de seguridad HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF para APIs REST sin sesiones
                .authorizeHttpRequests(auth -> auth
                        // Permite el acceso sin autenticación a los endpoints de autenticación
                        .requestMatchers("/api/auth/**").permitAll()
                        // Define otras rutas que no requieren autenticación (por ejemplo, documentación de Swagger)
                        // .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // Todas las demás solicitudes requieren autenticación
                        .anyRequest().authenticated()
                )
                // Configura la gestión de sesiones para que sea STATELESS (sin estado)
                // Esto es crucial para JWT, ya que cada solicitud lleva su propio token
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Añade nuestro AuthenticationProvider personalizado
                .authenticationProvider(authenticationProvider())
                // Añade el filtro JWT antes del filtro de usuario/contraseña de Spring Security
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}