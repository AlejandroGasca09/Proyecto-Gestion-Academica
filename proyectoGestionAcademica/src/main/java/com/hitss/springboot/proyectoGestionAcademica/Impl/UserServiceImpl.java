package com.hitss.springboot.proyectoGestionAcademica.Impl;

import com.hitss.springboot.proyectoGestionAcademica.entities.Role;
import com.hitss.springboot.proyectoGestionAcademica.entities.User;
import com.hitss.springboot.proyectoGestionAcademica.repositories.RoleRepository;
import com.hitss.springboot.proyectoGestionAcademica.repositories.UserRepository;
import com.hitss.springboot.proyectoGestionAcademica.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService  {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> roleOptionalUser = roleRepository.findByName("ROLE_USER");
        roleOptionalUser.ifPresent(roles::add);
        if (user.isAdmin()){
            Optional<Role> roleOptionalAdmin = roleRepository.findByName("ROLE_ADMIN");
            roleOptionalAdmin.ifPresent(roles::add);
        }
        if (user.isTeacher()){
            Optional<Role> roleOptionalTeacher = roleRepository.findByName("ROLE_TEACHER");
            roleOptionalTeacher.ifPresent(roles::add);
        }
        if (user.isStudent()) {
            Optional<Role> roleOptionalStudent = roleRepository.findByName("ROLE_STUDENT");
            roleOptionalStudent.ifPresent(roles::add);
        }
        user.setRoles(roles);
        //BCrypt
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
