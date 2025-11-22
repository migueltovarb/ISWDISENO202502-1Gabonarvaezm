package com.residencial.acceso.service;

import com.residencial.acceso.dto.NotificacionDTO;
import com.residencial.acceso.model.Notificacion;

import java.util.List;

public interface INotificacionService {
    Notificacion enviar(NotificacionDTO dto);
    List<Notificacion> listarPorResidente(String residenteId);
    Notificacion marcarLeida(String id);
    List<Notificacion> listarNoLeidas();
    void eliminar(String id);
}