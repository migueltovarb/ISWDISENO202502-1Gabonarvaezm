package com.residencial.acceso.dto;

import com.residencial.acceso.model.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioDTO {
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    private Rol rol;
    @Email
    private String email;
    @Size(min = 8)
    private String contrasena;
    private String apartamento;
    private Boolean activo;
}