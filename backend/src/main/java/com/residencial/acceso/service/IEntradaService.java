package com.residencial.acceso.service;

import com.residencial.acceso.dto.EntradaResidenteRequest;
import com.residencial.acceso.dto.EntradaVisitanteRequest;
import com.residencial.acceso.model.Entrada;

import java.util.List;

public interface IEntradaService {
    Entrada registrarEntradaVisitante(EntradaVisitanteRequest request);
    Entrada registrarEntradaResidente(EntradaResidenteRequest request);
    List<Entrada> listarTodas();
    void eliminar(String id);
}