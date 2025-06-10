package com.hitss.springboot.proyectoGestionAcademica.controllers;

import com.hitss.springboot.proyectoGestionAcademica.entities.PeriodoLectivo;
import com.hitss.springboot.proyectoGestionAcademica.repositories.PeriodoLectivoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/periodos")
public class PeriodoLectivoController {

    @Autowired
    private PeriodoLectivoRepository periodoLectivoRepository;

    // Crear
    @PostMapping
    public ResponseEntity<PeriodoLectivo> create(@Valid @RequestBody PeriodoLectivo periodoLectivo) {
        PeriodoLectivo saved = periodoLectivoRepository.save(periodoLectivo);
        return ResponseEntity.ok(saved);
    }

    // Listar todos
    @GetMapping
    public List<PeriodoLectivo> getAll() {
        return periodoLectivoRepository.findAll();
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<PeriodoLectivo> getById(@PathVariable Long id) {
        Optional<PeriodoLectivo> periodo = periodoLectivoRepository.findById(id);
        return periodo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<PeriodoLectivo> update(@PathVariable Long id, @Valid @RequestBody PeriodoLectivo input) {
        Optional<PeriodoLectivo> existing = periodoLectivoRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PeriodoLectivo periodo = existing.get();
        periodo.setName(input.getName());
        periodo.setFechaInicio(input.getFechaInicio());
        periodo.setFechaFin(input.getFechaFin());

        PeriodoLectivo updated = periodoLectivoRepository.save(periodo);
        return ResponseEntity.ok(updated);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!periodoLectivoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        periodoLectivoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
