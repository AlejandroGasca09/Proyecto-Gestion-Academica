package com.hitss.springboot.proyectoGestionAcademica.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "materiales")
@NoArgsConstructor
@Getter
@Setter
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String url;

    @ManyToOne
    @JoinColumn(name = "asignatura_id", referencedColumnName = "id")
    private Asignatura asignatura;

    @ManyToMany
    @JoinTable(
            name = "material_teachers",
            joinColumns = @JoinColumn(name = "material_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private List<Teacher> teachers;
}
