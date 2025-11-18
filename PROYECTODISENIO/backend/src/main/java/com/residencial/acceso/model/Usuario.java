package com.residencial.acceso.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;
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
    private boolean activo = true;
}