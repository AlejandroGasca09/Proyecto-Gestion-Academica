package com.hitss.springboot.proyectoGestionAcademica.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "matriculas")
@NoArgsConstructor
@Getter
@Setter
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id",referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id",referencedColumnName = "id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "periodo_id",referencedColumnName = "id")
    private PeriodoLectivo periodoLectivo;
}
