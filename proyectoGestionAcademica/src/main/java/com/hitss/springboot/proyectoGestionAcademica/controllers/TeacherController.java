package com.hitss.springboot.proyectoGestionAcademica.controllers;

import com.hitss.springboot.proyectoGestionAcademica.dto.TeacherDto;
import com.hitss.springboot.proyectoGestionAcademica.services.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;  // Usa la interfaz

    @PostMapping
    public ResponseEntity<TeacherDto> create(@Valid @RequestBody TeacherDto dto) {
        TeacherDto created = teacherService.createTeacher(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<TeacherDto>> getAll() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDto> update(@PathVariable Long id, @Valid @RequestBody TeacherDto dto) {
        return ResponseEntity.ok(teacherService.updateTeacher(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}
