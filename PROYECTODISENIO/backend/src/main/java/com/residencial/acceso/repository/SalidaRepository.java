package com.residencial.acceso.repository;

import com.residencial.acceso.model.Salida;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SalidaRepository extends MongoRepository<Salida, String> {
    boolean existsByEntradaId(String entradaId);
}