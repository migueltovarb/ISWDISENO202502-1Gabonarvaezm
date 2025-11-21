package com.residencial.acceso.controller;

import com.residencial.acceso.dto.NotificacionDTO;
import com.residencial.acceso.model.Notificacion;
import com.residencial.acceso.service.INotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
@Tag(name = "Notificaciones", description = "Gestión de notificaciones")
@SecurityRequirement(name = "bearerAuth")
public class NotificacionController {
    private final INotificacionService notificacionService;

    @PostMapping
    @Operation(summary = "Enviar notificación", description = "Enviar notificación a residente")
    public Notificacion enviar(@Valid @RequestBody NotificacionDTO dto) {
        return notificacionService.enviar(dto);
    }

    @GetMapping("/residente/{residenteId}")
    @Operation(summary = "Listar por residente", description = "Obtener notificaciones de un residente")
    public List<Notificacion> listarPorResidente(@PathVariable String residenteId) {
        return notificacionService.listarPorResidente(residenteId);
    }

    @GetMapping("/no-leidas")
    @Operation(summary = "Listar no leídas", description = "Obtener notificaciones no leídas")
    public List<Notificacion> listarNoLeidas() {
        return notificacionService.listarNoLeidas();
    }

    @PatchMapping("/{id}/leer")
    @Operation(summary = "Marcar como leída", description = "Marcar notificación como leída")
    public Notificacion marcarLeida(@PathVariable String id) {
        return notificacionService.marcarLeida(id);
    }
}