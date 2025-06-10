package com.hitss.springboot.proyectoGestionAcademica.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.naming.ldap.PagedResultsControl;

@Entity
@Table(name = "notes")
@NoArgsConstructor
@Getter
@Setter
public class Note {

    @ManyToOne
    @JoinColumn(name = "student_id",referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "asignatura_id",referencedColumnName = "id")
    private Asignatura asignatura;

    @ManyToOne
    @JoinColumn(name = "periodo_id",referencedColumnName = "id")
    private PeriodoLectivo periodoLectivo;

    @NotNull
    private Float valor;

    private String observaciones;
}
