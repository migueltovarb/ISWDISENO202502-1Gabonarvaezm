package com.residencial.acceso.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EntradaResidenteRequest {
    @NotBlank
    private String residenteId;
    @NotBlank
    private String vigilanteId;
    private String torre;
    private String apartamento;
    private String observaciones;
}