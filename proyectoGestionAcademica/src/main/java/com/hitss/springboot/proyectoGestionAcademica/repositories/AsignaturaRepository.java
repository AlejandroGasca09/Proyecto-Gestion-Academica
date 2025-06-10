package com.hitss.springboot.proyectoGestionAcademica.repositories;

import com.hitss.springboot.proyectoGestionAcademica.entities.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
}
