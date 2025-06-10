package com.hitss.springboot.proyectoGestionAcademica.repositories;

import com.hitss.springboot.proyectoGestionAcademica.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
}
