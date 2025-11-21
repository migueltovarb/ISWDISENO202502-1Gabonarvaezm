package com.residencial.acceso.repository;

import com.residencial.acceso.model.Visitante;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VisitanteRepository extends MongoRepository<Visitante, String> {
    Optional<Visitante> findByDocumento(String documento);
}