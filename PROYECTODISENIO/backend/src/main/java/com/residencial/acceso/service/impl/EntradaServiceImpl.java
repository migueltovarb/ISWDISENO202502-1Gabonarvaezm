package com.residencial.acceso.service.impl;

import com.residencial.acceso.dto.EntradaResidenteRequest;
import com.residencial.acceso.dto.EntradaVisitanteRequest;
import com.residencial.acceso.dto.NotificacionDTO;
import com.residencial.acceso.exception.BusinessException;
import com.residencial.acceso.exception.NotFoundException;
import com.residencial.acceso.model.Entrada;
import com.residencial.acceso.model.TipoEntrada;
import com.residencial.acceso.model.Usuario;
import com.residencial.acceso.model.Visitante;
import com.residencial.acceso.repository.EntradaRepository;
import com.residencial.acceso.repository.UsuarioRepository;
import com.residencial.acceso.repository.VisitanteRepository;
import com.residencial.acceso.service.IEntradaService;
import com.residencial.acceso.service.INotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntradaServiceImpl implements IEntradaService {
    private final EntradaRepository entradaRepository;
    private final UsuarioRepository usuarioRepository;
    private final VisitanteRepository visitanteRepository;
    private final INotificacionService notificacionService;

    @Override
    public Entrada registrarEntradaVisitante(EntradaVisitanteRequest request) {
        Objects.requireNonNull(request.getDocumentoVisitante());
        Objects.requireNonNull(request.getResidenteId());
        Objects.requireNonNull(request.getVigilanteId());
        Visitante visitante = visitanteRepository.findByDocumento(request.getDocumentoVisitante())
                .orElseThrow(() -> new NotFoundException("Visitante no encontrado"));
        
        Usuario residente = usuarioRepository.findById(request.getResidenteId())
                .orElseThrow(() -> new NotFoundException("Residente no encontrado"));
        
        Usuario vigilante = usuarioRepository.findById(request.getVigilanteId())
                .orElseThrow(() -> new NotFoundException("Vigilante no encontrado"));

        if (vigilante.getRol() != com.residencial.acceso.model.Rol.VIGILANTE) {
            throw new BusinessException("Solo un vigilante puede registrar entradas");
        }

        Entrada entrada = new Entrada();
        entrada.setVisitanteId(visitante.getId());
        entrada.setTipo(TipoEntrada.VISITANTE);
        entrada.setRegistradoPor(vigilante.getNombre() + " " + vigilante.getApellido());
        
        Entrada entradaGuardada = entradaRepository.save(entrada);
        
        // Enviar notificación al residente
        NotificacionDTO notificacionDTO = new NotificacionDTO();
        notificacionDTO.setResidenteId(residente.getId());
        notificacionDTO.setMensaje("Visitante " + visitante.getNombre() + " ha ingresado al conjunto");
        notificacionService.enviar(notificacionDTO);
        
        return entradaGuardada;
    }

    @Override
    public Entrada registrarEntradaResidente(EntradaResidenteRequest request) {
        Objects.requireNonNull(request.getResidenteId());
        Objects.requireNonNull(request.getVigilanteId());
        Usuario residente = usuarioRepository.findById(request.getResidenteId())
                .orElseThrow(() -> new NotFoundException("Residente no encontrado"));
        
        Usuario vigilante = usuarioRepository.findById(request.getVigilanteId())
                .orElseThrow(() -> new NotFoundException("Vigilante no encontrado"));

        if (vigilante.getRol() != com.residencial.acceso.model.Rol.VIGILANTE) {
            throw new BusinessException("Solo un vigilante puede registrar entradas");
        }

        Entrada entrada = new Entrada();
        entrada.setUsuarioId(residente.getId());
        entrada.setTipo(TipoEntrada.RESIDENTE);
        entrada.setRegistradoPor(vigilante.getNombre() + " " + vigilante.getApellido());
        
        return entradaRepository.save(entrada);
    }

    @Override
    public List<Entrada> listarTodas() {
        return entradaRepository.findAll();
    }

    @Override
    public void eliminar(String id) {
        Entrada entrada = entradaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entrada no encontrada"));
        entradaRepository.delete(entrada);
    }
}