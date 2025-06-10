package com.hitss.springboot.proyectoGestionAcademica.services;
import com.hitss.springboot.proyectoGestionAcademica.dto.TeacherDto;
import com.hitss.springboot.proyectoGestionAcademica.entities.Teacher;
import com.hitss.springboot.proyectoGestionAcademica.entities.User;
import com.hitss.springboot.proyectoGestionAcademica.repositories.TeacherRepository;
import com.hitss.springboot.proyectoGestionAcademica.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImp implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    private TeacherDto toDto(Teacher teacher) {
        TeacherDto dto = new TeacherDto();
        dto.setId(teacher.getId());
        dto.setEspecialidad(teacher.getEspecialidad());
        if (teacher.getUser() != null) {
            dto.setUserId(teacher.getUser().getId());
        }
        return dto;
    }

    private Teacher toEntity(TeacherDto dto) {
        Teacher teacher = new Teacher();
        teacher.setEspecialidad(dto.getEspecialidad());
        return teacher;
    }

    @Override
    public TeacherDto createTeacher(@Valid TeacherDto dto) {
        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("El usuario asociado es obligatorio");
        }

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Teacher teacher = toEntity(dto);
        teacher.setUser(user);

        Teacher saved = teacherRepository.save(teacher);
        return toDto(saved);
    }

    @Override
    public List<TeacherDto> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDto getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profesor no encontrado"));
        return toDto(teacher);
    }

    @Override
    public TeacherDto updateTeacher(Long id, @Valid TeacherDto dto) {
        Teacher existing = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profesor no encontrado"));

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            existing.setUser(user);
        }

        existing.setEspecialidad(dto.getEspecialidad());
        Teacher updated = teacherRepository.save(existing);
        return toDto(updated);
    }

    @Override
    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new EntityNotFoundException("Profesor no encontrado");
        }
        teacherRepository.deleteById(id);
    }
}
