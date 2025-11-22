package com.residencial.acceso.service.impl;

import com.residencial.acceso.dto.NotificacionDTO;
import com.residencial.acceso.exception.NotFoundException;
import com.residencial.acceso.model.Notificacion;
import com.residencial.acceso.repository.NotificacionRepository;
import com.residencial.acceso.repository.UsuarioRepository;
import com.residencial.acceso.service.INotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements INotificacionService {
    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public Notificacion enviar(NotificacionDTO dto) {
        usuarioRepository.findById(dto.getResidenteId())
                .orElseThrow(() -> new NotFoundException("Residente no encontrado"));

        Notificacion notificacion = new Notificacion();
        notificacion.setResidenteId(dto.getResidenteId());
        notificacion.setMensaje(dto.getMensaje());
        return notificacionRepository.save(notificacion);
    }

    @Override
    public List<Notificacion> listarPorResidente(String residenteId) {
        return notificacionRepository.findByResidenteId(residenteId);
    }

    @Override
    public Notificacion marcarLeida(String id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Notificación no encontrada"));
        notificacion.setLeida(true);
        return notificacionRepository.save(notificacion);
    }

    @Override
    public List<Notificacion> listarNoLeidas() {
        return notificacionRepository.findByLeidaFalse();
    }

    @Override
    public void eliminar(String id) {
        java.util.Objects.requireNonNull(id);
        Notificacion n = notificacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Notificación no encontrada"));

        org.springframework.security.core.Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new com.residencial.acceso.exception.UnauthorizedException("No autenticado");
        }

        Object principal = auth.getPrincipal();
        com.residencial.acceso.model.Usuario usuarioActual = principal instanceof com.residencial.acceso.security.UserDetailsImpl
                ? ((com.residencial.acceso.security.UserDetailsImpl) principal).getUsuario()
                : null;

        if (usuarioActual == null) {
            throw new com.residencial.acceso.exception.UnauthorizedException("No autenticado");
        }

        boolean permitido = usuarioActual.getRol() == com.residencial.acceso.model.Rol.ADMIN
                || n.getResidenteId().equals(usuarioActual.getId());
        if (!permitido) {
            throw new com.residencial.acceso.exception.UnauthorizedException("No autorizado");
        }

        notificacionRepository.delete(n);
    }
}