package com.hitss.springboot.proyectoGestionAcademica.controllers;

import com.hitss.springboot.proyectoGestionAcademica.entities.Material;
import com.hitss.springboot.proyectoGestionAcademica.repositories.AsignaturaRepository;
import com.hitss.springboot.proyectoGestionAcademica.repositories.TeacherRepository;
import com.hitss.springboot.proyectoGestionAcademica.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiales")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private TeacherRepository teacherRepository;


    @GetMapping("/asignatura/{id}")
    public List<Material> getByAsignatura(@PathVariable Long id) {
        return materialService.getAllMaterials();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }
}
