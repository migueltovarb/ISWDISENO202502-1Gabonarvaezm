package com.residencial.acceso.service;

import com.residencial.acceso.dto.VisitanteDTO;
import com.residencial.acceso.model.Visitante;

import java.util.List;

public interface IVisitaService {
    Visitante crear(VisitanteDTO dto);
    Visitante actualizar(String id, VisitanteDTO dto);
    void eliminar(String id);
    Visitante obtenerPorId(String id);
    List<Visitante> listar();
    List<Visitante> listarPorResidente(String residenteId);
}