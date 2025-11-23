package com.residencial.acceso.service.impl;

import com.residencial.acceso.dto.NotificacionDTO;
import com.residencial.acceso.dto.SalidaRequest;
import com.residencial.acceso.exception.BusinessException;
import com.residencial.acceso.exception.NotFoundException;
import com.residencial.acceso.model.Entrada;
import com.residencial.acceso.model.Salida;
import com.residencial.acceso.model.Visitante;
import com.residencial.acceso.repository.EntradaRepository;
import com.residencial.acceso.repository.SalidaRepository;
import com.residencial.acceso.repository.UsuarioRepository;
import com.residencial.acceso.repository.VisitanteRepository;
import com.residencial.acceso.service.ISalidaService;
import com.residencial.acceso.service.INotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalidaServiceImpl implements ISalidaService {
    private final SalidaRepository salidaRepository;
    private final EntradaRepository entradaRepository;
    private final UsuarioRepository usuarioRepository;
    private final VisitanteRepository visitanteRepository;
    private final INotificacionService notificacionService;

    @Override
    public Salida registrarSalida(SalidaRequest request) {
        Entrada entrada = entradaRepository.findById(request.getEntradaId())
                .orElseThrow(() -> new NotFoundException("Entrada no encontrada"));
        
        if (salidaRepository.existsByEntradaId(request.getEntradaId())) {
            throw new BusinessException("Ya existe una salida registrada para esta entrada");
        }

        if (request.getVigilanteId() == null || request.getVigilanteId().isBlank()) {
            throw new BusinessException("Vigilante requerido");
        }
        com.residencial.acceso.model.Usuario vigilante = usuarioRepository.findById(request.getVigilanteId())
                .orElseThrow(() -> new NotFoundException("Vigilante no encontrado"));
        if (vigilante.getRol() == null || vigilante.getRol() != com.residencial.acceso.model.Rol.VIGILANTE) {
            throw new BusinessException("Solo un vigilante puede registrar salidas");
        }

        Salida salida = new Salida();
        salida.setEntradaId(entrada.getId());
        String apellido = vigilante.getApellido() != null ? vigilante.getApellido() : "";
        salida.setRegistradoPor(vigilante.getNombre() + (apellido.isEmpty() ? "" : (" " + apellido)));
        
        Salida guardada = salidaRepository.save(salida);
        if (entrada.getVisitanteId() != null) {
            Visitante v = visitanteRepository.findById(entrada.getVisitanteId()).orElse(null);
            if (v != null) {
                try { notificacionService.enviarGeneral("Salida de visitante: " + v.getNombre()); } catch (Exception ignored) {}
            }
        }
        return guardada;
    }

    @Override
    public List<Salida> listarTodas() {
        return salidaRepository.findAll();
    }

    @Override
    public void eliminar(String id) {
        Salida salida = salidaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Salida no encontrada"));
        salidaRepository.delete(salida);
    }
}