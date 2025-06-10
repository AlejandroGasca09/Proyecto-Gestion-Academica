package com.hitss.springboot.proyectoGestionAcademica.controllers;

import com.hitss.springboot.proyectoGestionAcademica.dto.StudentDto;
import com.hitss.springboot.proyectoGestionAcademica.entities.Student;
import com.hitss.springboot.proyectoGestionAcademica.entities.User;
import com.hitss.springboot.proyectoGestionAcademica.repositories.StudentRepository;
import com.hitss.springboot.proyectoGestionAcademica.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    // Crear estudiante
    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentDto studentDTO) {
        Optional<User> userOpt = userRepository.findById(studentDTO.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        Student student = new Student();
        student.setUser(userOpt.get());
        student.setCdoMatricula(studentDTO.getCdoMatricula());
        student.setCroActual(studentDTO.getCroActual());

        studentRepository.save(student);

        studentDTO.setId(student.getId());
        return ResponseEntity.ok(studentDTO);
    }

    // Obtener todos los estudiantes
    @GetMapping
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener un estudiante por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        Optional<Student> studentOpt = studentRepository.findById(id);
        if (studentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(studentOpt.get()));
    }

    // Actualizar estudiante
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDto studentDTO) {
        Optional<Student> studentOpt = studentRepository.findById(id);
        if (studentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<User> userOpt = userRepository.findById(studentDTO.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        Student student = studentOpt.get();
        student.setUser(userOpt.get());
        student.setCdoMatricula(studentDTO.getCdoMatricula());
        student.setCroActual(studentDTO.getCroActual());

        studentRepository.save(student);

        return ResponseEntity.ok(convertToDTO(student));
    }

    // Eliminar estudiante
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Método auxiliar para convertir entidad a DTO
    private StudentDto convertToDTO(Student student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setUserId(student.getUser().getId());
        dto.setCdoMatricula(student.getCdoMatricula());
        dto.setCroActual(student.getCroActual());
        return dto;
    }
}
