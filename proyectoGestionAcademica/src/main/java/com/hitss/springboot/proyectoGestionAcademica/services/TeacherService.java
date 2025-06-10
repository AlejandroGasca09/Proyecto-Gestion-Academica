package com.hitss.springboot.proyectoGestionAcademica.services;

import com.hitss.springboot.proyectoGestionAcademica.dto.TeacherDto;
import jakarta.validation.Valid;

import java.util.List;

public interface TeacherService {
    TeacherDto createTeacher(@Valid TeacherDto dto);
    List<TeacherDto> getAllTeachers();
    TeacherDto getTeacherById(Long id);
    TeacherDto updateTeacher(Long id, @Valid TeacherDto dto);
    void deleteTeacher(Long id);
}
