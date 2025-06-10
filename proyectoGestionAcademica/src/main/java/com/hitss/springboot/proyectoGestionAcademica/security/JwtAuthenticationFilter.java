package com.hitss.springboot.proyectoGestionAcademica.security;

import com.hitss.springboot.proyectoGestionAcademica.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final com.hitss.springboot.proyectoGestionAcademica.security.jwt.JwtService jwtService;
    private final CustomUserDetailsService userDetailsService; // Inyectamos nuestro UserDetailsService

    public JwtAuthenticationFilter(com.hitss.springboot.proyectoGestionAcademica.security.jwt.JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); // Obtiene el header de autorización
        final String jwt;
        final String userEmail;

        // 1. Verifica si el header de autorización existe y tiene el formato esperado
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Si no es válido, continúa la cadena de filtros
            return;
        }

        // 2. Extrae el JWT del header (excluyendo "Bearer ")
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt); // Extrae el email del usuario del token

        // 3. Si se encontró el email y el usuario no está ya autenticado en el contexto de seguridad
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 4. Carga los detalles del usuario usando nuestro CustomUserDetailsService
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 5. Valida el token JWT
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // 6. Si el token es válido, crea un objeto de autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Las credenciales son nulas porque ya estamos autenticando con el token
                        userDetails.getAuthorities() // Los roles/autoridades del usuario
                );
                // 7. Establece los detalles de la solicitud (como la IP del cliente)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // 8. Establece el objeto de autenticación en el SecurityContextHolder
                // Esto indica a Spring Security que el usuario actual está autenticado
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 9. Continúa la cadena de filtros
        filterChain.doFilter(request, response);
    }
}