package com.hitss.springboot.proyectoGestionAcademica.services;
import com.hitss.springboot.proyectoGestionAcademica.entities.Role;
import com.hitss.springboot.proyectoGestionAcademica.repositories.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role createRole(@Valid Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role no encontrado con id: " + id));
    }

    @Override
    public Role updateRole(Long id, @Valid Role role) {
        Role existing = getRoleById(id);
        existing.setName(role.getName());
        // Otros campos si tiene
        return roleRepository.save(existing);
    }

    @Override
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new EntityNotFoundException("Role no encontrado con id: " + id);
        }
        roleRepository.deleteById(id);
    }
}
