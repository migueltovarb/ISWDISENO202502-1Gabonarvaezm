package com.residencial.acceso.service.impl;

import com.residencial.acceso.dto.NotificacionDTO;
import com.residencial.acceso.exception.NotFoundException;
import com.residencial.acceso.model.Notificacion;
import com.residencial.acceso.repository.NotificacionRepository;
import com.residencial.acceso.repository.UsuarioRepository;
import com.residencial.acceso.service.INotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements INotificacionService {
    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public Notificacion enviar(NotificacionDTO dto) {
        Objects.requireNonNull(dto.getResidenteId());
        Objects.requireNonNull(dto.getMensaje());
        usuarioRepository.findById(dto.getResidenteId())
                .orElseThrow(() -> new NotFoundException("Residente no encontrado"));

        Notificacion notificacion = new Notificacion();
        notificacion.setResidenteId(dto.getResidenteId());
        notificacion.setMensaje(dto.getMensaje());
        return notificacionRepository.save(notificacion);
    }

    @Override
    public List<Notificacion> listarPorResidente(String residenteId) {
        Objects.requireNonNull(residenteId);
        return notificacionRepository.findByResidenteId(residenteId);
    }

    @Override
    public Notificacion marcarLeida(String id) {
        Objects.requireNonNull(id);
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Notificación no encontrada"));
        notificacion.setLeida(true);
        return notificacionRepository.save(notificacion);
    }

    @Override
    public List<Notificacion> listarNoLeidas() {
        return notificacionRepository.findByLeidaFalse();
    }
}