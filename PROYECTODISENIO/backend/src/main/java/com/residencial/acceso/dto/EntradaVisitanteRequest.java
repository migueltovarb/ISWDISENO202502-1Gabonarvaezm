package com.residencial.acceso.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EntradaVisitanteRequest {
    @NotBlank
    private String documentoVisitante;
    @NotBlank
    private String residenteId;
    @NotBlank
    private String vigilanteId;
}