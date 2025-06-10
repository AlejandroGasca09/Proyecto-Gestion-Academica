package com.hitss.springboot.proyectoGestionAcademica.services;

import com.hitss.springboot.proyectoGestionAcademica.entities.PeriodoLectivo;
import jakarta.validation.Valid;
import java.util.List;

public interface PeriodoLectivoService {

    PeriodoLectivo createPeriodoLectivo(@Valid PeriodoLectivo periodoLectivo);

    List<PeriodoLectivo> getAllPeriodosLectivos();

    PeriodoLectivo getPeriodoLectivoById(Long id);

    PeriodoLectivo updatePeriodoLectivo(Long id, @Valid PeriodoLectivo periodoLectivo);

    void deletePeriodoLectivo(Long id);
}

