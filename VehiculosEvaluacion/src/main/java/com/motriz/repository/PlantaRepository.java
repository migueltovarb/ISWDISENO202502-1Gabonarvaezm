package com.motriz.repository;

import com.motriz.model.Planta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlantaRepository extends MongoRepository<Planta, String> {
    Optional<Planta> findByNombre(String nombre);
    List<Planta> findByFabricaId(String fabricaId);
}
