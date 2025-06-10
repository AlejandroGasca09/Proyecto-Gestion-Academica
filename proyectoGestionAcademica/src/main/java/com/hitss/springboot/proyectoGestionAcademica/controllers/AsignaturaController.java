package com.hitss.springboot.proyectoGestionAcademica.controllers;

import com.hitss.springboot.proyectoGestionAcademica.entities.Asignatura;
import com.hitss.springboot.proyectoGestionAcademica.services.AsignaturaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaturas")
public class AsignaturaController {

    @Autowired
    private AsignaturaService asignaturaService;

    @PostMapping
    public ResponseEntity<Asignatura> create(@Valid @RequestBody Asignatura asignatura) {
        return ResponseEntity.ok(asignaturaService.createAsignatura(asignatura));
    }

    @GetMapping
    public ResponseEntity<List<Asignatura>> getAll() {
        return ResponseEntity.ok(asignaturaService.getAllAsignaturas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asignatura> getById(@PathVariable Long id) {
        return ResponseEntity.ok(asignaturaService.getAsignaturaById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asignatura> update(@PathVariable Long id, @Valid @RequestBody Asignatura asignatura) {
        return ResponseEntity.ok(asignaturaService.updateAsignatura(id, asignatura));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        asignaturaService.deleteAsignatura(id);
        return ResponseEntity.noContent().build();
    }
}
