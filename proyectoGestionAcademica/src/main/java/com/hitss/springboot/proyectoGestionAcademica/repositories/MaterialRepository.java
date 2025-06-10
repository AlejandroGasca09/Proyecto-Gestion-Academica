package com.hitss.springboot.proyectoGestionAcademica.repositories;

import com.hitss.springboot.proyectoGestionAcademica.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

}
