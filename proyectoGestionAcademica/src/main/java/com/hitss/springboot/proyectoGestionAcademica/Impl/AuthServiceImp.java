package com.hitss.springboot.proyectoGestionAcademica.Impl;
import com.hitss.springboot.proyectoGestionAcademica.dto.AuthResponseDto;
import com.hitss.springboot.proyectoGestionAcademica.dto.LoginRequestDto;
import com.hitss.springboot.proyectoGestionAcademica.dto.RegisterRequestDto;
import com.hitss.springboot.proyectoGestionAcademica.entities.Role;
import com.hitss.springboot.proyectoGestionAcademica.entities.User;
import com.hitss.springboot.proyectoGestionAcademica.repositories.RoleRepository;
import com.hitss.springboot.proyectoGestionAcademica.repositories.UserRepository;
import com.hitss.springboot.proyectoGestionAcademica.security.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthServiceImp implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthServiceImp(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponseDto register(RegisterRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado.");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Role studentRole = roleRepository.findByName("ESTUDIANTE")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("ESTUDIANTE");
                    return roleRepository.save(newRole);
                });

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encodedPassword);
        newUser.setRoles(Collections.singletonList(studentRole));
        newUser.setEnabled(true);

        userRepository.save(newUser);

        String jwtToken = jwtService.generateToken(newUser);

        return new AuthResponseDto(jwtToken, "Registro exitoso", studentRole.getName(), newUser.getId(), newUser.getName());
    }
    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Obtiene los detalles del usuario autenticado para generar el token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado después de autenticación."));
        String jwtToken = jwtService.generateToken(user);
        String userRole = user.getRoles().isEmpty() ? "N/A" : user.getRoles().get(0).getName();
        return new AuthResponseDto(jwtToken, "Inicio de sesión exitoso", userRole, user.getId(), user.getName());
    }

    @Override // Indica que este método implementa un método de la interfaz
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Verifica si hay autenticación y si el principal no es una cadena (usuario anónimo o no autenticado)
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            throw new RuntimeException("No hay usuario autenticado.");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado en la base de datos."));
    }
}