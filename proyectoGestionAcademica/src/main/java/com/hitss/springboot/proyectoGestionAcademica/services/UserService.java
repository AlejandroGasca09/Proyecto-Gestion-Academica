package com.hitss.springboot.proyectoGestionAcademica.services;

import com.hitss.springboot.proyectoGestionAcademica.dto.UserDto;
import com.hitss.springboot.proyectoGestionAcademica.dto.UserUpdateRequestDTO;
import com.hitss.springboot.proyectoGestionAcademica.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    // Listado de todos los usuarios, devolviendo DTOs
    List<UserDto> findAllUsers();

    // Detalle de un usuario por ID, devolviendo DTO
    Optional<UserDto> findUserById(Long id);

    // Método para crear un usuario (puede ser usado por ADMIN).
    // Aquí podrías usar un UserCreateRequestDTO si la creación difiere mucho de la actualización.
    // Por ahora, reutilizaremos la lógica de registro básica o podríamos crear un método específico.
    // Para simplificar, la creación de usuarios con roles específicos por ADMIN se haría aquí.
    // UserDto createUser(UserCreateRequestDTO request); // Opción más robusta
    UserDto createUser(UserUpdateRequestDTO request); // Reutilizando para el ejemplo de ADMIN

    // Editar un usuario, recibiendo un DTO y devolviendo un DTO
    UserDto updateUser(Long id, UserUpdateRequestDTO request);

    // Eliminar un usuario
    void deleteUser(Long id);

    // Método auxiliar para obtener la entidad User real (puede ser útil internamente o para otros servicios)
    Optional<User> getUserEntityById(Long id);
}