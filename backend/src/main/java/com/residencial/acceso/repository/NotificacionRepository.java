package com.residencial.acceso.repository;

import com.residencial.acceso.model.Notificacion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificacionRepository extends MongoRepository<Notificacion, String> {
    List<Notificacion> findByResidenteId(String residenteId);
    List<Notificacion> findByLeidaFalse();
}