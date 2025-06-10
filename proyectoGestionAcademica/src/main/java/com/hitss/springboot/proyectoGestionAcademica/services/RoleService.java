package com.hitss.springboot.proyectoGestionAcademica.services;

import com.hitss.springboot.proyectoGestionAcademica.entities.Role;
import jakarta.validation.Valid;

import java.util.List;

public interface RoleService {

    Role createRole(@Valid Role role);

    List<Role> getAllRoles();

    Role getRoleById(Long id);

    Role updateRole(Long id, @Valid Role role);

    void deleteRole(Long id);
}
