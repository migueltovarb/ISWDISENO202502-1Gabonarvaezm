package com.residencial.acceso.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VisitanteDTO {
    @NotBlank
    private String nombre;
    @NotBlank
    private String documento;
    private String residenteVisitado;
    private String motivoVisita;
    private String fotoOpcionalUrl;
}