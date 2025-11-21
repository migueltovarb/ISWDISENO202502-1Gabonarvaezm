package com.residencial.acceso.repository;

import com.residencial.acceso.model.Entrada;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EntradaRepository extends MongoRepository<Entrada, String> {
    List<Entrada> findByVisitanteId(String visitanteId);
    List<Entrada> findByUsuarioId(String usuarioId);
}