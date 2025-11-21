package com.motriz.repository;

import com.motriz.model.Vehiculo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehiculoRepository extends MongoRepository<Vehiculo, String> {
    List<Vehiculo> findByPlantaId(String plantaId);
    List<Vehiculo> findByMarca(String marca);
}
