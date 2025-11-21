package com.residencial.acceso.service;

import com.residencial.acceso.dto.SalidaRequest;
import com.residencial.acceso.model.Salida;

import java.util.List;

public interface ISalidaService {
    Salida registrarSalida(SalidaRequest request);
    List<Salida> listarTodas();
    void eliminar(String id);
}