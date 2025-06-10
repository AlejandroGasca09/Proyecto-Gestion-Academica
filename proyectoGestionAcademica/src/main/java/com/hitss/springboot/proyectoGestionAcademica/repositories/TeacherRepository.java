package com.hitss.springboot.proyectoGestionAcademica.repositories;

import com.hitss.springboot.proyectoGestionAcademica.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
