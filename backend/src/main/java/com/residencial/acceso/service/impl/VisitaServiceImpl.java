package com.residencial.acceso.service.impl;

import com.residencial.acceso.dto.VisitanteDTO;
import com.residencial.acceso.exception.NotFoundException;
import com.residencial.acceso.model.Visitante;
import com.residencial.acceso.repository.VisitanteRepository;
import com.residencial.acceso.service.IVisitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitaServiceImpl implements IVisitaService {
    private final VisitanteRepository visitanteRepository;

    @Override
    public Visitante crear(VisitanteDTO dto) {
        Visitante visitante = new Visitante();
        visitante.setNombre(dto.getNombre());
        visitante.setDocumento(dto.getDocumento());
        visitante.setResidenteVisitado(dto.getResidenteVisitado());
        visitante.setMotivoVisita(dto.getMotivoVisita());
        visitante.setFotoOpcionalUrl(dto.getFotoOpcionalUrl());
        return visitanteRepository.save(visitante);
    }

    @Override
    public Visitante actualizar(String id, VisitanteDTO dto) {
        Visitante visitante = obtenerPorId(id);
        visitante.setNombre(dto.getNombre());
        visitante.setDocumento(dto.getDocumento());
        visitante.setResidenteVisitado(dto.getResidenteVisitado());
        visitante.setMotivoVisita(dto.getMotivoVisita());
        visitante.setFotoOpcionalUrl(dto.getFotoOpcionalUrl());
        return visitanteRepository.save(visitante);
    }

    @Override
    public void eliminar(String id) {
        Visitante visitante = obtenerPorId(id);
        visitanteRepository.delete(visitante);
    }

    @Override
    public Visitante obtenerPorId(String id) {
        return visitanteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Visitante no encontrado"));
    }

    @Override
    public List<Visitante> listar() {
        return visitanteRepository.findAll();
    }

    @Override
    public List<Visitante> listarPorResidente(String residenteId) {
        return visitanteRepository.findAll().stream()
                .filter(v -> residenteId.equals(v.getResidenteVisitado()))
                .toList();
    }
}