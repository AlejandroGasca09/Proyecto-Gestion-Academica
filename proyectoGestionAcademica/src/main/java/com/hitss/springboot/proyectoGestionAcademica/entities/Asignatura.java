package com.hitss.springboot.proyectoGestionAcademica.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "asignaturas")
@Getter
@Setter
@NoArgsConstructor
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "teacher_id" , referencedColumnName = "id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "curso_id", referencedColumnName = "id")
    private Course curso;

}
