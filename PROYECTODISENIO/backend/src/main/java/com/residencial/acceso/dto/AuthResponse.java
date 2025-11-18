package com.residencial.acceso.dto;

import com.residencial.acceso.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String usuarioId;
    private String nombre;
    private Rol rol;
}