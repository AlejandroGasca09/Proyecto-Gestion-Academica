package com.hitss.springboot.proyectoGestionAcademica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {

    private Long id;

    @NotNull
    private Long userId;

    @NotBlank
    private String cdoMatricula;

    @NotBlank
    private String croActual;
}
