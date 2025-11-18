package com.residencial.acceso.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SalidaRequest {
    @NotBlank
    private String entradaId;
    @NotBlank
    private String vigilanteId;
}