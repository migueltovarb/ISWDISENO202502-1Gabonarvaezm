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

        org.springframework.security.core.Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        com.residencial.acceso.model.Usuario vigilante = auth instanceof org.springframework.security.authentication.UsernamePasswordAuthenticationToken
                ? ((com.residencial.acceso.security.UserDetailsImpl) auth.getPrincipal()).getUsuario()
                : null;
        if (vigilante == null || vigilante.getRol() == null || vigilante.getRol() != com.residencial.acceso.model.Rol.VIGILANTE) {
            throw new BusinessException("Solo un vigilante puede registrar salidas");
        }

        Salida salida = new Salida();
        salida.setEntradaId(entrada.getId());
        salida.setRegistradoPor(vigilante.getNombre() + " " + vigilante.getApellido());
        
        Salida guardada = salidaRepository.save(salida);
        if (entrada.getVisitanteId() != null) {
            Visitante v = visitanteRepository.findById(entrada.getVisitanteId()).orElse(null);
            if (v != null && v.getResidenteVisitado() != null) {
                NotificacionDTO n = new NotificacionDTO();
                n.setResidenteId(v.getResidenteVisitado());
                n.setMensaje("Se registró salida de " + v.getNombre());
                notificacionService.enviar(n);
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