package com.hitss.springboot.proyectoGestionAcademica.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students")
@NoArgsConstructor
@Getter
@Setter
public class Student {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @NotBlank
    private String cdoMatricula;

    @NotBlank
    private String croActual;
}
