package com.hitss.springboot.proyectoGestionAcademica.repositories;

import com.hitss.springboot.proyectoGestionAcademica.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Aquí puedes agregar métodos personalizados si los necesitas
}
