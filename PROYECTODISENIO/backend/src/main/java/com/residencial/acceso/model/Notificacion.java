package com.residencial.acceso.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "notificaciones")
public class Notificacion {
    @Id
    private String id;
    private String residenteId;
    private String mensaje;
    private Instant fecha = Instant.now();
    private boolean leida = false;
}