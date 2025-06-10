package com.hitss.springboot.proyectoGestionAcademica.services;

import com.hitss.springboot.proyectoGestionAcademica.entities.Asignatura;
import jakarta.validation.Valid;

import java.util.List;

public interface AsignaturaService {

    Asignatura createAsignatura(@Valid Asignatura asignatura);

    List<Asignatura> getAllAsignaturas();

    Asignatura getAsignaturaById(Long id);

    Asignatura updateAsignatura(Long id, @Valid Asignatura updated);

    void deleteAsignatura(Long id);
}
