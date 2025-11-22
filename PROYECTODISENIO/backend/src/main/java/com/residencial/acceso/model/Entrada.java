package com.residencial.acceso.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "entradas")
public class Entrada {
    @Id
    private String id;
    private String visitanteId;
    private String usuarioId;
    private TipoEntrada tipo;
    private Instant horaEntrada = Instant.now();
    private String registradoPor;
    private String torre;
    private String apartamento;
    private String observaciones;
}