package com.hitss.springboot.proyectoGestionAcademica.controllers;

import com.hitss.springboot.proyectoGestionAcademica.dto.AuthResponseDto;
import com.hitss.springboot.proyectoGestionAcademica.dto.LoginRequestDto;
import com.hitss.springboot.proyectoGestionAcademica.dto.RegisterRequestDto;
import com.hitss.springboot.proyectoGestionAcademica.entities.User;
import com.hitss.springboot.proyectoGestionAcademica.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController // Indica que es un controlador REST
@RequestMapping("/api/auth") // Ruta base para todos los endpoints de este controlador
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint para el registro de nuevos usuarios.
     * @param request DTO con los datos de registro (nombre, email, password).
     * @return ResponseEntity con el JWT y mensaje de éxito, o un error.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        try {
            AuthResponseDTO response = authService.register(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED); // Código 201 Created
        } catch (RuntimeException e) {
            // Aquí puedes manejar diferentes tipos de excepciones para errores más específicos
            // Por ejemplo, EmailAlreadyExistsException
            return new ResponseEntity<>(new AuthResponseDTO(null, e.getMessage(), null, null, null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint para el inicio de sesión de usuarios.
     * @param request DTO con las credenciales (email, password).
     * @return ResponseEntity con el JWT y mensaje de éxito, o un error.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        try {
            AuthResponseDTO response = authService.login(request);
            return new ResponseEntity<>(response, HttpStatus.OK); // Código 200 OK
        } catch (RuntimeException e) {
            // Para errores de autenticación (credenciales inválidas)
            return new ResponseEntity<>(new AuthResponseDTO(null, "Credenciales inválidas", null, null, null), HttpStatus.UNAUTHORIZED); // Código 401 Unauthorized
        }
    }

    /**
     * Endpoint para obtener los datos del usuario autenticado.
     * Requiere que el usuario esté autenticado con un JWT válido.
     * @return ResponseEntity con los datos del usuario.
     */
    @GetMapping("/me")
    // @PreAuthorize("isAuthenticated()") // Opcional: Para asegurar que solo usuarios autenticados puedan acceder
    public ResponseEntity<User> getCurrentUser() {
        try {
            User currentUser = authService.getCurrentUser();
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Este error solo debería ocurrir si el contexto de seguridad está vacío o corrupto
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // O un 404 si el usuario no se encuentra
        }
    }
}