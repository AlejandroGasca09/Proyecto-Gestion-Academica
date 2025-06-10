package com.hitss.springboot.proyectoGestionAcademica.security;

import com.hitss.springboot.proyectoGestionAcademica.entities.User;
import com.hitss.springboot.proyectoGestionAcademica.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Carga los detalles del usuario por su nombre de usuario (en este caso, el email).
     * Este método es utilizado por Spring Security durante el proceso de autenticación.
     * @param email El email del usuario que se intenta autenticar.
     * @return Un objeto UserDetails que representa el usuario.
     * @throws UsernameNotFoundException Si el usuario no es encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscamos el usuario por email. Si no se encuentra, lanzamos una excepción.
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + email));

        // Convertimos los roles del usuario a una colección de GrantedAuthority,
        // que es lo que Spring Security espera.
        // Los roles se prefijan con "ROLE_" por convención de Spring Security.
        Collection<? extends GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());

        // Devolvemos un objeto UserDetails de Spring Security.
        // Usamos el constructor de Spring Security User para construirlo.
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),       // Nombre de usuario (email)
                user.getPassword(),    // Contraseña (ya codificada)
                user.isEnabled(),      // Si el usuario está habilitado (lo agregamos en tu entidad User)
                true,                  // Cuenta no expirada
                true,                  // Credenciales no expiradas
                true,                  // Cuenta no bloqueada
                authorities            // Roles/autoridades del usuario
        );
    }
}