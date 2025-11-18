package com.residencial.acceso.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "visitantes")
public class Visitante {
    @Id
    private String id;
    @NotBlank
    private String nombre;
    @NotBlank
    private String documento;
    private String residenteVisitado;
    private String motivoVisita;
    private Instant fechaRegistro = Instant.now();
    private String fotoOpcionalUrl;
}