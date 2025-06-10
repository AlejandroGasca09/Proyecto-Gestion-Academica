package com.hitss.springboot.proyectoGestionAcademica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    private String jwt;
    private String message;
    private String userRole;
    private Long userId;
    private String userName;
}