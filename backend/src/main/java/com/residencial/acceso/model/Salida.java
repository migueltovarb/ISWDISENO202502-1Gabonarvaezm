package com.residencial.acceso.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "salidas")
public class Salida {
    @Id
    private String id;
    private String entradaId;
    private Instant horaSalida = Instant.now();
    private String registradoPor;
}